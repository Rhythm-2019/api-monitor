package org.mdnote.apiWatch.aggregator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.Metric;
import org.mdnote.apiWatch.aggregator.calculator.ICalculator;

/**
 * @author Rhythm-2019
 *
 * 该类用于聚合运算
 */
public class Aggregator {
    
    private List<ICalculator> calculators;

    public Aggregator() {
        calculators = new ArrayList<>();
    }

    public void addCalculator(ICalculator calculator) {
        this.calculators.add(calculator);
    }

    public void addCalculator(ICalculator... calculators) {
        this.calculators.addAll(Arrays.asList(calculators));
    }


    public AggregateResult aggregate(List<Metric> metrics, long durationInMillis ) {
        
        AggregateResult aggregateResult = new AggregateResult();
        
        this.calculators.forEach(calculator -> {
            calculator.calculate(metrics, durationInMillis, aggregateResult);
        });

        return aggregateResult;
    }
    
}
