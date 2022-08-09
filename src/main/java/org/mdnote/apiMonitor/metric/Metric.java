package org.mdnote.apiMonitor.metric;

import lombok.Data;
import org.mdnote.apiMonitor.annotation.MetricField;
import org.mdnote.apiMonitor.annotation.Timestamp;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description 指标
 */
@Data
public class Metric {

    /**
     * 时间戳 ms
     */
    @Timestamp
    protected long timestamp = System.currentTimeMillis();
    /**
     * 服务名称
     */
    @MetricField("serverName")
    protected String serverName;
    /**
     * URI 地址
     */
    @MetricField("uri")
    protected String uri;
    protected Metric() {
    }
}
