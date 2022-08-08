package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;

/**
 * @author Rhythm-2019
 */
public interface Calculator {
    // TODO 这里设计其实不太好，clientMetric 和 serverMetric 没有关系，Calculator 应该只关注聚合操作，不应该感知到整个 result

    /**
     * 聚合
     *
     * @param clientMetricList 客户端指标数据
     * @param serverMetricList 服务端指标数据
     * @param durationMillis   聚合时间间隔
     * @param aggregateResult  聚合结果
     * @throws AggregateException 聚合异常
     */
    void calculate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis, AggregateResult aggregateResult) throws AggregateException;
}
