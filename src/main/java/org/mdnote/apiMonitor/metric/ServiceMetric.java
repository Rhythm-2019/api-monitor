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
public class ServiceMetric extends Metric {

    private String collector = "server";

    private long spendTime;
    private int httpStatusCode;

    @Override
    public String measurement() {
        return String.format("%s:%s:%s", super.serverName, super.uri, this.collector);
    }
}
