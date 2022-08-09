package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.metric.ServerMetric;

/**
 * @author Rhythm-2019
 * <p>
 * 服务端指标聚合接口
 */
@FunctionalInterface
public interface ServerAggregateCalculator extends AggregateCalculator<ServerMetric> {
}
