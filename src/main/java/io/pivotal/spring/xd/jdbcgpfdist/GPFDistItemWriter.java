package io.pivotal.spring.xd.jdbcgpfdist;

import io.pivotal.spring.xd.jdbcgpfdist.gpfdist.AbstractGPFDistMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;

/**
 * Created by cq on 27/3/16.
 */
public class GPFDistItemWriter implements ItemWriter<String>{

    private Logger logger = LoggerFactory.getLogger(GPFDistItemWriter.class);

    private AbstractGPFDistMessageHandler messageHandler;

    @Autowired
    public GPFDistItemWriter(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;

    }


    @Override
    public void write(List<? extends String> list) throws Exception {

        logger.info("processing list of messages with size: " + list.size());

        list.stream().forEach(m -> messageHandler.handleMessage(new GenericMessage<>(m)));

    }


}
