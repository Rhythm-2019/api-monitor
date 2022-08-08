package org.mdnote.apiMonitor.aggregator;

import org.mdnote.apiMonitor.aggregator.calculator.Calculator;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Rhythm-2019
 * <p>
 * 该类用于聚合运算
 * <p>
 * 调用 (@code aggregate) 方法完成聚合运算。计算器需要事先通过 (@code addCalculator) 方法传入
 */
public class Aggregator {

    /**
     * 计数器实现
     */
    private List<Calculator> calculatorList;

    public Aggregator() {
        calculatorList = new ArrayList<>();
    }

    /**
     * 添加计数器规则，计数器需要实现 (@link ICalculator) 接口
     *
     * @param calculator 计数器
     */
    public Aggregator addCalculator(Calculator calculator) {
        this.calculatorList.add(calculator);
        return this;
    }

    public Aggregator addCalculators(Calculator... calculatorArray) {
        Collections.addAll(this.calculatorList, calculatorArray);
        return this;
    }

    /**
     * 一次添加多个计数器
     *
     * @param calculators 计数器数组
     */
    public Aggregator addCalculator(Calculator... calculators) {
        this.calculatorList.addAll(Arrays.asList(calculators));
        return this;
    }

    public AggregateResult aggregate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis) {
        AggregateResult aggregateResult = new AggregateResult();
        for (Calculator calculator : this.calculatorList) {
            try {
                calculator.calculate(clientMetricList, serverMetricList, durationMillis, aggregateResult);
            } catch (Throwable t) {
                throw new AggregateException("unexpected exception", t);
            }
        }

        return aggregateResult;
    }
}
