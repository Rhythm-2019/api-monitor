package org.mdnote.apiMonitor.metric;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.mdnote.apiMonitor.annotation.MetricField;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description 客户端指标
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientMetric extends Metric {
    /**
     * 响应时间
     */
    @MetricField("rt")
    private long rt;


}
