package org.mdnote.apiMonitor.aggregator;

import org.mdnote.apiMonitor.aggregator.calculator.AggregateCalculator;
import org.mdnote.apiMonitor.aggregator.calculator.ClientAggregateCalculator;
import org.mdnote.apiMonitor.aggregator.calculator.ServerAggregateCalculator;
import org.mdnote.apiMonitor.annotation.CalculateBy;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rhythm-2019
 * <p>
 * 该类用于聚合运算
 * <p>
 * 调用 (@code aggregate) 方法完成聚合运算。计算器需要事先通过 (@code addCalculator) 方法传入
 */
public class Aggregator {

    /**
     * 结果类型
     */
    private Class<? extends AggregateResult> resultClz;
    /**
     * 客户端相关聚合方法
     */
    private Map<Field, ClientAggregateCalculator> clientAggregateCalculatorMap;

    /**
     * 服务端相关聚合方法
     */
    private Map<Field, ServerAggregateCalculator> serverAggregateCalculatorList;

    public Aggregator(Class<? extends AggregateResult> resultClz) {
        this.resultClz = resultClz;
        this.clientAggregateCalculatorMap = new HashMap<>();
        this.serverAggregateCalculatorList = new HashMap<>();
        try {
            this.scanAnnotation();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AggregateException("scan annotation failed", e);
        }
    }

    public AggregateResult aggregate(List<ClientMetric> clientMetricList, List<ServerMetric> serverMetricList, int durationMillis) {
        try {
            AggregateResult aggregateResult = this.resultClz.newInstance();

            // 设置默认字段
            aggregateResult.setDuration(durationMillis);

            // 聚合客户端指标
            for (Map.Entry<Field, ClientAggregateCalculator> fieldClientAggregateCalculatorEntry : this.clientAggregateCalculatorMap.entrySet()) {
                Field field = fieldClientAggregateCalculatorEntry.getKey();
                ClientAggregateCalculator calculator = fieldClientAggregateCalculatorEntry.getValue();

                Object calculateResult = calculator.calculate(clientMetricList, durationMillis);
                if (calculateResult == null) {
                    continue;
                }
                if (field.getType() != calculateResult.getClass()) {
                    throw new AggregateException(String.format("filed %s type can not cast to calculate method result type %s", field.getName(), calculateResult.getClass().getName()), null);
                }
                field.set(aggregateResult, calculateResult);
            }

            // 聚合服务端指标
            for (Map.Entry<Field, ServerAggregateCalculator> fieldServerAggregateCalculatorEntry : this.serverAggregateCalculatorList.entrySet()) {
                Field field = fieldServerAggregateCalculatorEntry.getKey();
                ServerAggregateCalculator calculator = fieldServerAggregateCalculatorEntry.getValue();

                Object calculateResult = calculator.calculate(serverMetricList, durationMillis);
                if (calculateResult == null) {
                    continue;
                }
                if (field.getType() != calculateResult.getClass()) {
                    throw new AggregateException(String.format("filed %s type can not cast to calculate method result type %s", field.getName(), calculateResult.getClass().getName()), null);
                }
                field.set(aggregateResult, calculateResult);
            }

            return aggregateResult;
        } catch (Exception e) {
            throw new AggregateException("reflect set result to object failed", e);
        }
    }

    private void scanAnnotation() throws InstantiationException, IllegalAccessException {
        // 获取所有 Calculator 并初始化，返回 Field-Calculator Map
        Map<Field, AggregateCalculator<?>> fieldAggregateCalculatorMap = getFieldCalcMap(this.resultClz);

        for (Map.Entry<Field, AggregateCalculator<?>> fieldAggregateCalculatorEntry : fieldAggregateCalculatorMap.entrySet()) {
            Field field = fieldAggregateCalculatorEntry.getKey();
            AggregateCalculator<?> aggregateCalculator = fieldAggregateCalculatorEntry.getValue();
            if (aggregateCalculator instanceof ClientAggregateCalculator) {
                ClientAggregateCalculator clientAggregateCalculator = (ClientAggregateCalculator) aggregateCalculator;
                this.clientAggregateCalculatorMap.put(field, clientAggregateCalculator);
            }
            if (aggregateCalculator instanceof ServerAggregateCalculator) {
                ServerAggregateCalculator serverAggregateCalculator = (ServerAggregateCalculator) aggregateCalculator;
                this.serverAggregateCalculatorList.put(field, serverAggregateCalculator);
            }
        }
    }

    private Map<Field, AggregateCalculator<?>> getFieldCalcMap(Class<? extends AggregateResult> targetClz) throws IllegalAccessException, InstantiationException {
        HashMap<Field, AggregateCalculator<?>> resultMap = new HashMap<>();
        for (Field field : targetClz.getDeclaredFields()) {
            CalculateBy annotation = field.getAnnotation(CalculateBy.class);
            if (annotation == null) {
                continue;
            }
            Class<? extends AggregateCalculator<?>> aggregateCalculatorClz = annotation.value();

            AggregateCalculator<?> aggregateCalculator = aggregateCalculatorClz.newInstance();
            field.setAccessible(true);
            resultMap.put(field, aggregateCalculator);
        }
        return resultMap;
    }
}
