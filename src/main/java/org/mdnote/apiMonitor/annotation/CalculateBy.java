package org.mdnote.apiMonitor.annotation;

import org.mdnote.apiMonitor.aggregator.calculator.AggregateCalculator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Rhythm-2019
 * @date 2022/8/8
 * @description 指定聚合函数，目的是借助反射保证 calculate 的职责单一
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CalculateBy {
    Class<? extends AggregateCalculator<?>> value();
}
