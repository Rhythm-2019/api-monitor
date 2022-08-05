package org.mdnote.apiMonitor.aggregator;

import java.util.*;

import org.mdnote.apiMonitor.aggregator.calculator.Calculator;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

/**
 * @author Rhythm-2019
 *
 * 该类用于聚合运算
 *
 * 调用 (@code aggregate) 方法完成聚合运算。计算器需要事先通过 (@code addCalculator) 方法传入
 */
public class Aggregator {

    /**
     * 计数器实现
     */
    private List<Calculator> calculators;

    public Aggregator() {
        calculators = new ArrayList<>();
    }

    /**
     * 添加计数器规则，计数器需要实现 (@link ICalculator) 接口
     * @param calculator 计数器
     */
    public void addCalculator(Calculator calculator) {
        this.calculators.add(calculator);
    }

    /**
     * 一次添加多个计数器
     * @param calculators 计数器数组
     */
    public void addCalculator(Calculator... calculators) {
        this.calculators.addAll(Arrays.asList(calculators));
    }

    public AggregateResult aggregate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis) {
        AggregateResult aggregateResult = new AggregateResult();
        this.calculators.forEach(calculator -> {
            calculator.calculate(clientMetricList, serverMetricList, durationMillis, aggregateResult);
        });

        return aggregateResult;
    }
}
