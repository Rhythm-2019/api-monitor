package org.mdnote.apiMonitor.aggregator.calculator;

import java.util.List;
import java.util.Optional;

import org.mdnote.apiMonitor.AggregateResult;

public class MaxRespCalculator implements ICalculator {

    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        if (metrics != null && !metrics.isEmpty()) {
            Optional<Long> optional = metrics.stream()
                    .map(Metric::getResponseTime)
                    .reduce((rt1, rt2) -> rt1 > rt2 ? rt1 : rt2);
            result.setMaxRespTime(optional.get());
        } else {
            result.setMaxRespTime(-1);
        }
    }
}
