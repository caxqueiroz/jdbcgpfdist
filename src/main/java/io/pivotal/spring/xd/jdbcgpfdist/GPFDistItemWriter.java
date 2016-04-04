package io.pivotal.spring.xd.jdbcgpfdist;

import io.pivotal.spring.xd.jdbcgpfdist.support.AbstractGPFDistMessageHandler;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

/**
 * Created by cq on 27/3/16.
 */
public class GPFDistItemWriter implements ItemWriter<String>{

    private AbstractGPFDistMessageHandler messageHandler;

    @Autowired
    public GPFDistItemWriter(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }


    @Override
    public void write(List<? extends String> list) throws Exception {
        list.stream().forEach(m -> messageHandler.handleMessage(MessageBuilder.withPayload(m).build()));
    }


}
