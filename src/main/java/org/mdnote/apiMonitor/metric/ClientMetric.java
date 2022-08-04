package org.mdnote.apiMonitor.metric;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientMetric extends Metric {
    @MetricField("rt")
    private long rt;


}
