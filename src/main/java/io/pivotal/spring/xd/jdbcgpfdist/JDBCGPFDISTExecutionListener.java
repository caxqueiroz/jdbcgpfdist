package io.pivotal.spring.xd.jdbcgpfdist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;


/**
 * Created by cq on 29/3/16.
 */
public class JDBCGPFDISTExecutionListener implements StepExecutionListener, JobExecutionListener{

    private final Log log = LogFactory.getLog(JDBCGPFDISTExecutionListener.class);

    private GPFDistMessageHandler messageHandler;

    public JDBCGPFDISTExecutionListener(GPFDistMessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info("check the status: "+ stepExecution.getStatus());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        return ExitStatus.COMPLETED;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info("starting gpfdist...");
        messageHandler.start();
        log.info("started gpfdist!");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        messageHandler.stop();
    }
}
