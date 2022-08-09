package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

public class MaxServerSpendTimeCalculator implements ServerAggregateCalculator {

    @Override
    public Object calculate(List<ServerMetric> metricList, long durationMillis) throws AggregateException {
        if (metricList.isEmpty()) {
            return null;
        }
        return metricList.stream()
                .map(ServerMetric::getSpendTime)
                .reduce(Long::sum).get() * 1.0 / metricList.size();
    }
}
