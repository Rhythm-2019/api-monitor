package org.mdnote.apiMonitor.aggregator.calculator;

import java.util.List;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

/**
 * @author Rhythm
 */
public interface Calculator {
    void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult);
}
