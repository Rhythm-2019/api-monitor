package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 平均 RT = 总 RT / 请求数
 */
public class MeanRTCalculator implements ClientAggregateCalculator {

    @Override
    public Object calculate(List<ClientMetric> metricList, long durationMillis) throws AggregateException {
        if (metricList.isEmpty()) {
            return null;
        }
        return metricList.stream()
                .map(ClientMetric::getRt)
                .reduce(Long::sum)
                .get() * 1.0 / metricList.size();

    }
}
