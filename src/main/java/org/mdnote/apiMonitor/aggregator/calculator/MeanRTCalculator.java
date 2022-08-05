package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 平均 RT = 总 RT / 请求数
 */
public class MeanRTCalculator implements Calculator {
    @Override
    public void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) {
        if (clientMetricList.isEmpty()) {
            return;
        }
        aggregateResult.setMeanRT(clientMetricList.stream()
                .map(ClientMetric::getRt)
                .reduce(Long::sum)
                .get() * 1.0 / clientMetricList.size());
    }
}