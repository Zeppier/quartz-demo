package com.intempt.demo.quartzdemo.pull;

import com.intempt.demo.quartzdemo.timers.JobInfo;
import com.intempt.demo.quartzdemo.timers.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PullSourceService { //will be using webflux

    private final SchedulerService schedulerService;

    public void addJob1() {
        schedulerService.schedule(SourcePuller.class, "1",
                new JobInfo(0, true, 10000,  1000,
                        new PullSourceData(1L, 1L, "singer")));
    }

    public void addJob2() {
        schedulerService.schedule(SourcePuller.class, "2",
                new JobInfo(0, true, 5000,  1000,
                        new PullSourceData(1L, 2L, "singer")));
    }

    public Boolean delete(String id) {
        return schedulerService.deleteJob(SourcePuller.class, id);
    }

    public void update1() {
        schedulerService.updateTrigger(SourcePuller.class, "1", new JobInfo(0, true, 2000,  1000,
                new PullSourceData(1L, 1L, "1")));
    }

    public void update2() {
        schedulerService.updateTrigger(SourcePuller.class, "2", new JobInfo(0, true, 2000,  1000,
                new PullSourceData(2L, 2L, "2")));
    }

    public List<JobInfo> getAll() {
        return schedulerService.getScheduledJobs(SourcePuller.class);
    }

    public JobInfo get(String id) {
        return schedulerService.getScheduledJob(SourcePuller.class, id);
    }




}
