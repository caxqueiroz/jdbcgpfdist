package io.pivotal.spring.xd.jdbcgpfdist;

import io.pivotal.spring.xd.jdbcgpfdist.gpfdist.AbstractGPFDistMessageHandler;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by cq on 27/3/16.
 */
public class GPFDistItemWriter implements ItemWriter<Map<String,Object>>{

    private AbstractGPFDistMessageHandler messageHandler;

    @Autowired
    public GPFDistItemWriter(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }


    @Override
    public void write(List<? extends Map<String, Object>> list) throws Exception {

        list.stream().forEach(m -> messageHandler.handleMessage(new GenericMessage<Map>(m)));

    }
}
