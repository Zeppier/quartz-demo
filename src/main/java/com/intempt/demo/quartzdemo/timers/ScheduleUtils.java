package com.intempt.demo.quartzdemo.timers;

import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class ScheduleUtils {

    public JobDetail buildJobDetail(Class jobClass,  String id, JobInfo info) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

       return JobBuilder
                .newJob(jobClass)
                .withIdentity(JobKey.jobKey(id, jobClass.getSimpleName()))
                .setJobData(jobDataMap)
                .requestRecovery(true) //if fails with error other node will execute it
                .build();

    }

    public Trigger buildTrigger(Class jobClass, String id, JobInfo info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(info.getRepeatIntervalMs());

        if(info.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(TriggerKey.triggerKey(id, jobClass.getSimpleName()))
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }


}
