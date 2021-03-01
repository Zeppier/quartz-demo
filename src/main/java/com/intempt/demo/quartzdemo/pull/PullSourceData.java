package com.intempt.demo.quartzdemo.pull;

import com.intempt.demo.quartzdemo.timers.ScheduledSourceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PullSourceData implements Serializable, ScheduledSourceData {
    Long orgId;

    Long sourceId;

    String type;

}
