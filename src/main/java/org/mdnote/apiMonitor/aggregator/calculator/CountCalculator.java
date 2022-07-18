package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.Metric;

import java.util.List;

public class CountCalculator implements ICalculator {

    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        result.setCount(metrics.size());
    }
}
