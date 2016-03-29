package io.pivotal.spring.xd.jdbcgpfdist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.xd.greenplum.gpfdist.AbstractGPFDistMessageHandler;

/**
 * Created by cq on 29/3/16.
 */
public class JobExecutionListener implements StepExecutionListener{

    private final Log log = LogFactory.getLog(JobExecutionListener.class);

    private AbstractGPFDistMessageHandler messageHandler;

    public JobExecutionListener(AbstractGPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Stopping status: "+ stepExecution.getStatus());
        messageHandler.stop();
        log.info("stopped status: "+ stepExecution.getStatus());
        return stepExecution.getExitStatus();
    }
}
