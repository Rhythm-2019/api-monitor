package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.metric.ClientMetric;

/**
 * @author Rhythm-2019
 * <p>
 * 聚合接口
 */
@FunctionalInterface
public interface ClientAggregateCalculator extends AggregateCalculator<ClientMetric> {
}
