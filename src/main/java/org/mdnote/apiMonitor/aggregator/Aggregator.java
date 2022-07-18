package org.mdnote.apiMonitor.aggregator;

import java.util.*;

import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.Metric;
import org.mdnote.apiMonitor.aggregator.calculator.ICalculator;

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
    private List<ICalculator> calculators;

    public Aggregator() {
        calculators = new ArrayList<>();
    }

    /**
     * 添加计数器规则，计数器需要实现 (@link ICalculator) 接口
     * @param calculator 计数器
     */
    public void addCalculator(ICalculator calculator) {
        this.calculators.add(calculator);
    }

    /**
     * 一次添加多个计数器
     * @param calculators 计数器数组
     */
    public void addCalculator(ICalculator... calculators) {
        this.calculators.addAll(Arrays.asList(calculators));
    }


    /**
     * 对单个 API 进行指标聚合运算
     * @param metricList 指标列表
     * @param durationInMillis 测量的时间间隔，单位为 ms，在计算 TPS 等参数时可以用上
     * @return 聚合结果
     */
    public AggregateResult aggregate(List<Metric> metricList, long durationInMillis ) {
        
        AggregateResult aggregateResult = new AggregateResult();
        
        this.calculators.forEach(calculator -> {
            calculator.calculate(metricList, durationInMillis, aggregateResult);
        });

        return aggregateResult;
    }

    /**
     * 对多个 API 进行指标聚合运算
     * @param metricMap 多个指标集合，key 时 apiName，value 是 apiName 为 key 的指标集合
     * @param durationInMillis 测量的时间间隔，单位为 ms，在计算 TPS 等参数时可以用上
     * @return 聚合结果，key 时 apiName，value 是 apiName 为 key 的聚合结果
     */
    public List<AggregateResult> aggregate(Map<String, List<Metric>> metricMap, long durationInMillis ) {

        List<AggregateResult> result = new ArrayList<>();

        metricMap.forEach((apiName, metricList) -> {
            AggregateResult aggregateResult = new AggregateResult();
            this.calculators.forEach(calculator -> {
                calculator.calculate(metricList, durationInMillis, aggregateResult);
            });

            result.add(aggregateResult);
        });

        return result;
    }
    
}
