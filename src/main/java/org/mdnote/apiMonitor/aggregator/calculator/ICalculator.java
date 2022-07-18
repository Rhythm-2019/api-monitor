package org.mdnote.apiMonitor.aggregator.calculator;

import java.util.List;

import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.Metric;

public interface ICalculator {
    void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result);
}
