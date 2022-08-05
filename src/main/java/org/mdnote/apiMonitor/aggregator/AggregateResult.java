package org.mdnote.apiMonitor.aggregator;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AggregateResult {
    private long count;
    private Double qps;
    private Double meanRT;
    private Double minRT;
    private Double maxRT;
    private Double p99RT;
    private Double p999RT;
    private Double meanServerSpendTime;
    private Double maxServerSpendTime;
}
