package org.mdnote.apiMonitor.aggregator.calculator;

import java.util.List;

import org.mdnote.apiMonitor.AggregateResult;

public interface ICalculator {
    void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result);
}
