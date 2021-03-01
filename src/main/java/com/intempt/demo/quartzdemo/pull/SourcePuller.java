package com.intempt.demo.quartzdemo.pull;

import com.intempt.demo.quartzdemo.timers.JobInfo;
import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;

import static java.lang.Thread.sleep;

@Component
@DisallowConcurrentExecution
public class SourcePuller implements Job {

    private final Logger logger = LoggerFactory.getLogger(SourcePuller.class);
/*
type - singer
source id
last run
 */
    @SneakyThrows
    public Mono<Void> pullSource(PullSourceData data, Date date) {
        logger.info("Starting processing job " + data);
//        sleep(1000); //some hard logic
//        logger.info("Finishing processing job " + data);
        return Mono.empty();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobInfo info = (JobInfo) jobDataMap.get(SourcePuller.class.getSimpleName());
        pullSource((PullSourceData) info.getCallbackData(), jobExecutionContext.getPreviousFireTime()).subscribe();
    }
}
