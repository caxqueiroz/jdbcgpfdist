package io.pivotal.spring.xd.jdbcgpfdist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by cq on 27/3/16.
 */
public class GPFDistItemWriter implements ItemWriter<String>{

    private final Log log = LogFactory.getLog(GPFDistItemWriter.class);

    private GPFDistMessageHandler messageHandler;

    @Autowired
    public GPFDistItemWriter(GPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    @Override
    public void write(List<? extends String> list) throws Exception {

        list.stream().forEach(m -> messageHandler.sendMessage(m));
    }

}