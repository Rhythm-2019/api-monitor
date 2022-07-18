package org.mdnote.apiWatch;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AggregateResult {
    private String apiName;
    private long durationSecond;
    private double avgRespTime;
    private long maxRespTime;
    private long minRespTime;
    private long count;
    private long p99RespTime;
    private long p999RespTime;
    private long tps;
}
