package com.intempt.demo.quartzdemo.timers;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    private final Scheduler scheduler;

    private final ScheduleUtils scheduleUtils;

    //create
    public void schedule(Class jobClass, String id, JobInfo info) {
        JobDetail jobDetail = scheduleUtils.buildJobDetail(jobClass, id, info);
        Trigger trigger = scheduleUtils.buildTrigger(jobClass, id, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //get all scheduled jobs
    public List<JobInfo> getScheduledJobs(Class jobClass) {
        try {
            return scheduler.getJobKeys(GroupMatcher.groupEquals(jobClass.getSimpleName()))
                    .stream()
                    .map(y -> {
                        try {
                            JobDetail detail = scheduler.getJobDetail(y);
                            return (JobInfo) detail.getJobDataMap().get(jobClass.getSimpleName());
                        } catch (SchedulerException e) {
                            logger.error(e.getMessage(), e);
                            return null;
                        }

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    //get one scheduled job
    public JobInfo getScheduledJob(Class jobClass, String id) {
        try {
            return (JobInfo) scheduler.getJobDetail(JobKey.jobKey(id, jobClass.getSimpleName()))
                    .getJobDataMap()
                    .get(jobClass.getSimpleName());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    //update
    public void updateTrigger(Class jobClass, String id, JobInfo info) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(id, jobClass.getSimpleName()));
            if (jobDetail == null) {
                logger.error("job with id {} not found", id);
                return;
            }

            jobDetail.getJobDataMap().put(id, info);
            scheduler.rescheduleJob(TriggerKey.triggerKey(id, jobClass.getSimpleName()),
                    scheduleUtils.buildTrigger(jobClass, id, info));
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    //delete
    public Boolean deleteJob(Class jobClass, String id) {
        try {
            return scheduler.deleteJob(JobKey.jobKey(id, jobClass.getSimpleName()));
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @PostConstruct
    private void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    private void shutdown() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
