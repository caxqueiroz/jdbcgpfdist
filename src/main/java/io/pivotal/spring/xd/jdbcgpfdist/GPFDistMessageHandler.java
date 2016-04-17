/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pivotal.spring.xd.jdbcgpfdist;

import com.codahale.metrics.Meter;
import io.pivotal.spring.xd.jdbcgpfdist.support.GreenplumLoad;
import io.pivotal.spring.xd.jdbcgpfdist.support.NetworkUtils;
import io.pivotal.spring.xd.jdbcgpfdist.support.RuntimeContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import reactor.Environment;
import reactor.core.processor.RingBufferProcessor;
import reactor.io.buffer.Buffer;

public class GPFDistMessageHandler {

    private final Log log = LogFactory.getLog(GPFDistMessageHandler.class);

    private final int port;

    private final int flushCount;

    private final int flushTime;

    private final int batchTimeout;

    private final int batchCount;

    private final int batchPeriod;

    private final String delimiter;

    private GreenplumLoad greenplumLoad;

    private RingBufferProcessor<Buffer> processor;

    private GPFDistServer gpfdistServer;

    private int rateInterval = 0;

    private Meter meter = null;

    private int meterCount = 0;

    RuntimeContext context;

    public GPFDistMessageHandler(int port, int flushCount, int flushTime, int batchTimeout, int batchCount,
                                 int batchPeriod, String delimiter) {
        super();
        this.port = port;
        this.flushCount = flushCount;
        this.flushTime = flushTime;
        this.batchTimeout = batchTimeout;
        this.batchCount = batchCount;
        this.batchPeriod = batchPeriod;
        this.delimiter = StringUtils.hasLength(delimiter) ? delimiter : null;
    }


    public void start() {

        try {
            //init counter
            meterCount = 0;

            context = new RuntimeContext();

            Environment.initializeIfEmpty().assignErrorJournal();
            processor = RingBufferProcessor.create(false);

            if (rateInterval > 0) {
                meter = new Meter();
            }

            log.info("Creating gpfdist protocol listener on port=" + port);
            gpfdistServer = new GPFDistServer(processor, port, flushCount, flushTime, batchTimeout, batchCount);
            gpfdistServer.start();
            log.info("gpfdist protocol listener running on port=" + gpfdistServer.getLocalPort());
            context.addLocation(NetworkUtils.getGPFDistUri(gpfdistServer.getLocalPort()));


        } catch (Exception e) {
            throw new RuntimeException("Error starting protocol listener", e);
        }


    }


    public void stop() {

        boolean drained = false;
        if (greenplumLoad != null) {

            greenplumLoad.load(context);
            // xd waits 30s to shutdown module, so lets wait 25 to drain
            long waitDrain = System.currentTimeMillis() + 25000l;

            log.info("Trying to wait buffer to get drained");
            while (System.currentTimeMillis() < waitDrain) {
                long capacity = processor.getCapacity();
                long availableCapacity = processor.getAvailableCapacity();
                log.info("Buffer capacity " + capacity);
                log.info("Buffer available capacity " + availableCapacity);
                if (capacity != availableCapacity) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                    }
                }
                else {
                    log.info("Marking stream drained");
                    drained = true;
                    break;
                }
            }

        }

        try {
            if (drained) {
                log.info("Sending onComplete to processor");
                processor.onComplete();
            }
            else {
                // if it looks like we didn't drain,
                // force shutdown as onComplete will
                // block otherwise.
                log.info("Forcing processor shutdown");
                processor.forceShutdown();
            }
            log.info("Shutting down protocol listener");
            gpfdistServer.stop();

        }
        catch (Exception e) {
            log.warn("Error shutting down protocol listener", e);
        }
    }

    public void sendMessage(String message)  {

        if (delimiter != null) {
            processor.onNext(Buffer.wrap(message + delimiter));
        }
        else {
            processor.onNext(Buffer.wrap(message));
        }
        if (meter != null) {
            if ((meterCount++ % rateInterval) == 0) {
                meter.mark(rateInterval);
                log.info("METER: 1 minute rate = " + meter.getOneMinuteRate() + " mean rate = " + meter.getMeanRate());
                greenplumLoad.load(context);
            }
        }

    }


    public void setGreenplumLoad(GreenplumLoad greenplumLoad) {
        this.greenplumLoad = greenplumLoad;
    }

    public void setRateInterval(int rateInterval) {
        this.rateInterval = rateInterval;

    }
}