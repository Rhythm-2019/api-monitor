package org.mdnote.apiMonitor;

import lombok.Data;

@Data
public class Metric {
    private String apiName;
    private long currentTimestamp;
    private long responseTime;
}
