package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.Metric;

import java.util.List;
import java.util.Optional;

public class AvgRespCalculator implements ICalculator {

    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        if (metrics != null && !metrics.isEmpty()) {
            Optional<Long> optional = metrics.stream()
                    .map(Metric::getResponseTime)
                    .reduce(Long::sum);
            result.setAvgRespTime(optional.map(avg -> avg / metrics.size()).get());
        } else {
            result.setAvgRespTime(-1);
        }
    }
}