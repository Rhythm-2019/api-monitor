package org.mdnote.apiMonitor.metric;

import lombok.Data;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description
 */
@Data
public abstract class Metric {
    @Timestamp
    protected long timestamp;
    @MetricField("serverName")
    protected String serverName;
    @MetricField("uri")
    protected String uri;
}
