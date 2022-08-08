package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

public class P999RTCalculator implements Calculator {

    @Override
    public void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) throws AggregateException {
        if (clientMetricList.isEmpty()) {
            return;
        }
        clientMetricList.sort((m1, m2) -> (int) (m1.getRt() - m2.getRt()));
        aggregateResult.setP999RT((double) clientMetricList.get((int) (clientMetricList.size() * 0.999)).getRt());

    }
}
