package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

public class MaxServerSpendTimeCalculator implements Calculator {

    @Override
    public void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) throws AggregateException {
        if (serverMetricList.isEmpty()) {
            return;
        }
        aggregateResult.setMaxServerSpendTime(serverMetricList.stream()
                .map(ServerMetric::getSpendTime)
                .reduce(Long::sum).get() * 1.0 / serverMetricList.size());
    }
}
