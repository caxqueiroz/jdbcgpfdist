package io.pivotal.spring.xd.jdbcgpfdist.gpfdist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Base implementation of Spring Integration {@code MessageHandler} handling {@code Message}.
 *
 * @author Janne Valkealahti
 */
public abstract class AbstractGPFDistMessageHandler extends AbstractMessageHandler implements SmartLifecycle {

    private static final Log logger = LogFactory.getLog(AbstractGPFDistMessageHandler.class);

    private volatile boolean autoStartup = true;

    private volatile int phase = 0;

    private volatile boolean running;

    private final ReentrantLock lifecycleLock = new ReentrantLock();

    @Override
    public final boolean isAutoStartup() {
        return this.autoStartup;
    }

    @Override
    public final int getPhase() {
        return this.phase;
    }

    @Override
    public final boolean isRunning() {
        this.lifecycleLock.lock();
        try {
            return this.running;
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public final void start() {
        this.lifecycleLock.lock();
        try {
            if (!this.running) {
                this.doStart();
                this.running = true;
                if (logger.isInfoEnabled()) {
                    logger.info("started " + this);
                }
                else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("already started " + this);
                    }
                }
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public final void stop() {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                this.doStop();
                this.running = false;
                if (logger.isInfoEnabled()) {
                    logger.info("stopped " + this);
                }
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("already stopped " + this);
                }
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public final void stop(Runnable callback) {
        this.lifecycleLock.lock();
        try {
            this.stop();
            callback.run();
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    protected final void handleMessageInternal(Message<?> message) throws Exception {
        try {
            doWrite(message);
        }
        catch (Exception e) {
            throw new MessageHandlingException(message,
                    "failed to write Message payload to GPDB/HAWQ", e);
        }
    }

    /**
     * Sets the auto startup.
     *
     * @param autoStartup the new auto startup
     * @see SmartLifecycle
     */
    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    /**
     * Sets the phase.
     *
     * @param phase the new phase
     * @see SmartLifecycle
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * Subclasses may override this method with the start behaviour. This method will be invoked while holding the
     * {@link #lifecycleLock}.
     */
    protected void doStart() {
    };

    /**
     * Subclasses may override this method with the stop behaviour. This method will be invoked while holding the
     * {@link #lifecycleLock}.
     */
    protected void doStop() {
    };

    /**
     * Subclasses need to implement this method to handle {@link Message} in its writer.
     *
     * @param message the message to write
     */
    protected abstract void doWrite(Message<?> message) throws Exception;

}