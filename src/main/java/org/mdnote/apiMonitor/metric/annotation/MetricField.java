package org.mdnote.apiMonitor.metric.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description 指标字段，设计目的是为了让 Storage 实现时不用感知到具体业务逻辑
 * 比如
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetricField {
    String value() default "";
}
