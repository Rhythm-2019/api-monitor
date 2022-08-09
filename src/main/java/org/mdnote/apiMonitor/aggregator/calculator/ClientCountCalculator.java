package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 */
public class ClientCountCalculator implements ClientAggregateCalculator {

    @Override
    public Object calculate(List<ClientMetric> metricList, long durationMillis) throws AggregateException {
        return metricList.size();
    }
}
