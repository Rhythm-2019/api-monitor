package org.mdnote.apiMonitor.aggregator;

import org.mdnote.apiMonitor.aggregator.calculator.*;

/**
 * @author Rhythm-2019
 * @date 2022/8/8
 * @description 聚合类工厂，简化 Aggregator 的创建
 */
public class AggregatorFactory {

    private static Aggregator clientAggregator;
    private static Aggregator serverAggregator;

    static {
        clientAggregator = new Aggregator()
                .addCalculator(new ClientCountCalculator())
                .addCalculator(new P99RTCalculator())
                .addCalculator(new P999RTCalculator())
                .addCalculator(new MeanRTCalculator());
        serverAggregator = new Aggregator()
                .addCalculator(new ServerCountCalculator())
                .addCalculator(new MeanServerSpendTimeCalculator())
                .addCalculator(new MaxServerSpendTimeCalculator());
    }

    public static Aggregator clientAggregator() {
        return clientAggregator;
    }

    public static Aggregator serverAggregator() {
        return serverAggregator;
    }
}
