package org.mdnote.apiWatch.aggregator.calculator;

import java.util.List;

import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.Metric;

public interface ICalculator {
    void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result);
}
