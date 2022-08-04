package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.AggregateResult;

import java.util.List;

public class TpsCalculatorImpl implements ICalculator {
    @Override
    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        if (metrics != null && !metrics.isEmpty() && durationInMillis > 0) {
            result.setTps(metrics.size() / durationInMillis * 1000);
        } else {
            result.setTps(-1);
        }

    }
}
