package org.mdnote.apiWatch.aggregator.calculator;

import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.Metric;

import java.util.List;
import java.util.Optional;

public class CountCalculator implements ICalculator {

    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        result.setCount(metrics.size());
    }
}
