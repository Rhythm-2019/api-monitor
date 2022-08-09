package org.mdnote.apiMonitor.aggregator.calculator;

import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.Metric;

import java.util.List;

/**
 * @author Rhythm-2019
 * <p>
 * 聚合接口
 */
@FunctionalInterface
public interface AggregateCalculator<T extends Metric> {
    /**
     * 聚合
     *
     * @param metricList     指标集合
     * @param durationMillis 统计时间间隔
     * @return 聚合结果，会通过 AggregateResult 上的注解反射收集
     * @throws AggregateException 聚合异常，可以手动抛出
     */
    Object calculate(List<T> metricList, long durationMillis) throws AggregateException;

}
