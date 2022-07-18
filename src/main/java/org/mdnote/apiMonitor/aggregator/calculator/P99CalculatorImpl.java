package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.Metric;

import java.util.List;

public class P99CalculatorImpl implements ICalculator {
    @Override
    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        if (metrics != null && !metrics.isEmpty()) {
            metrics.sort(((m1, m2) -> (int) (m1.getResponseTime() - m2.getResponseTime())));
            result.setP99RespTime(metrics.get((int) (metrics.size() * 0.99)).getResponseTime());
        } else {
            result.setP99RespTime(-1);
        }
    }
}
