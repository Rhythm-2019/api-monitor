package org.mdnote.apiMonitor.aggregator.calculator;

import org.jetbrains.annotations.NotNull;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description QPS = PV / duration
 */
public class QpsCalculator implements ServerAggregateCalculator {

    @Override
    public Object calculate(@NotNull List<ServerMetric> metricList, long durationMillis) throws AggregateException {
        if (metricList.isEmpty()) {
            return null;
        }
        return metricList.size() * 1.0 / durationMillis;

    }
}
