package com.intempt.demo.quartzdemo.timers;

import com.intempt.demo.quartzdemo.pull.PullSourceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInfo implements Serializable {
    private int totalFireCount;

    private boolean runForever;

    private long repeatIntervalMs;

    private long initialOffsetMs;

    private ScheduledSourceData callbackData;

}
