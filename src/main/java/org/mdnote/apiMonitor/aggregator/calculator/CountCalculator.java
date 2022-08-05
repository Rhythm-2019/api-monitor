package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

public class CountCalculator implements Calculator {

    @Override
    public void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) {
        aggregateResult.setCount(serverMetricList.size());
    }
}
