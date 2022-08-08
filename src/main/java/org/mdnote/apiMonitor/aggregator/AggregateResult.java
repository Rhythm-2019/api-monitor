package org.mdnote.apiMonitor.aggregator;

import lombok.Data;
import lombok.ToString;

/**
 * @author Rhythm-2019
 * 聚合结果
 */
@Data
@ToString
public class AggregateResult {

    // TODO AggregateResult 职责不单一

    private Integer serverCount;
    private Double meanRT;
    private Double minRT;
    private Double maxRT;
    private Double p99RT;
    private Double p999RT;

    private Integer clientCount;
    private Double qps;
    private Double meanServerSpendTime;
    private Double maxServerSpendTime;
}
