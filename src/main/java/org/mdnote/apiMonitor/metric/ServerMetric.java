package org.mdnote.apiMonitor.metric;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mdnote.apiMonitor.metric.annotation.MetricField;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerMetric extends Metric {

    /**
     * 服务端处理请求花费时间
     */
    @MetricField("spend_time")
    private long spendTime;
    /**
     * 状态码
     */
    @MetricField("http_status_code")
    private int httpStatusCode;

}
