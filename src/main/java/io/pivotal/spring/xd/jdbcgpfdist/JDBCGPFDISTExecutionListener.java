package io.pivotal.spring.xd.jdbcgpfdist;

import io.pivotal.spring.xd.jdbcgpfdist.support.AbstractGPFDistMessageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


/**
 * Created by cq on 29/3/16.
 */
public class JDBCGPFDISTExecutionListener implements StepExecutionListener{

    private final Log log = LogFactory.getLog(JDBCGPFDISTExecutionListener.class);

    private AbstractGPFDistMessageHandler messageHandler;

    public JDBCGPFDISTExecutionListener(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("check the status: "+ stepExecution.getStatus());
        if(!messageHandler.isRunning()) {
            log.info("starting gpfdist...");
            messageHandler.start();
            log.info("started " + stepExecution.getStatus());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(messageHandler.isRunning()){
            messageHandler.stop();
            log.info("stopped status: "+ stepExecution.getStatus());
        }
        return ExitStatus.COMPLETED;
    }
}
