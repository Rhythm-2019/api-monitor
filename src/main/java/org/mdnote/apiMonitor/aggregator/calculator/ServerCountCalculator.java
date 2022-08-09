package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 */
public class ServerCountCalculator implements ServerAggregateCalculator {

    @Override
    public Object calculate(List<ServerMetric> metricList, long durationMillis) throws AggregateException {
        return metricList.size();
    }
}
