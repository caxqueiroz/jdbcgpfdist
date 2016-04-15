package io.pivotal.spring.xd.jdbcgpfdist;

import io.pivotal.spring.xd.jdbcgpfdist.support.AbstractGPFDistMessageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

/**
 * Created by cq on 27/3/16.
 */
public class GPFDistItemWriter implements ItemWriter<String>{

    private AbstractGPFDistMessageHandler messageHandler;
    private static final Log logger = LogFactory.getLog(GPFDistItemWriter.class);

    @Autowired
    public GPFDistItemWriter(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }


    @Override
    public void write(List<? extends String> list) throws Exception {
        list.stream().forEach(m -> {
            try {
                messageHandler.handleMessage(MessageBuilder.withPayload(m).build());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        });
    }


}
