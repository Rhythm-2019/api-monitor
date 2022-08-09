package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 */
public class P999RTCalculator implements ClientAggregateCalculator {


    @Override
    public Object calculate(List<ClientMetric> metricList, long durationMillis) throws AggregateException {
        if (metricList.isEmpty()) {
            return null;
        }
        metricList.sort((m1, m2) -> (int) (m1.getRt() - m2.getRt()));
        return (double) metricList.get((int) (metricList.size() * 0.999)).getRt();

    }
}
