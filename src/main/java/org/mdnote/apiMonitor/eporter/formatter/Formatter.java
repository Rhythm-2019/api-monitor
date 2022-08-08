package org.mdnote.apiMonitor.eporter.formatter;

import org.mdnote.apiMonitor.aggregator.AggregateResult;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 格式化接口，Terminal组合该接口实现不同格式的输出
 */
public interface Formatter {
    /**
     * 格式化
     *
     * @param aggregateResult 聚合结果
     * @return 格式化后的数据
     */
    String format(AggregateResult aggregateResult);
}
