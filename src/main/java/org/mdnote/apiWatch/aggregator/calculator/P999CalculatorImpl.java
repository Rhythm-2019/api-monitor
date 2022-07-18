package org.mdnote.apiWatch.aggregator.calculator;

import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.Metric;

import java.util.List;

public class P999CalculatorImpl implements ICalculator {
    @Override
    public void calculate(List<Metric> metrics, long durationInMillis, AggregateResult result) {
        if (metrics != null && !metrics.isEmpty()) {
            metrics.sort(((m1, m2) -> (int) (m1.getResponseTime() - m2.getResponseTime())));
            result.setP999RespTime(metrics.get((int) (metrics.size() * 0.999)).getResponseTime());
        } else {
            result.setP999RespTime(-1);
        }
    }
}
