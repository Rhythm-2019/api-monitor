package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description QPS = PV / duration
 */
public class QpsCalculator implements Calculator {
    @Override
    public void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) throws AggregateException {
        if (serverMetricList.isEmpty()) {
            return;
        }
        aggregateResult.setQps(serverMetricList.size() * 1.0 / durationMillis);
    }
}
