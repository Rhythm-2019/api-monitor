package org.mdnote.apiMonitor.metric;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description
 */
public class MetricAnnotationUtil {

    public static String timestampFieldName(Metric metric) {
        List<String> tsFields = Arrays.stream(metric.getClass().getFields())
                .filter(field -> field.getAnnotation(Timestamp.class) == null)
                .map(Field::getName)
                .collect(Collectors.toList());
        if (tsFields.size() == 0) {
            return null;
        }
        return tsFields.get(0);
    }

    public static Map<String, Field> getFieldMap(Class<?> clz) {
        return Arrays.stream(clz.getFields())
                .filter(field -> field.getAnnotation(MetricField.class) != null)
                .collect(Collectors.toMap(field -> field.getAnnotation(MetricField.class).value(), field -> field));
    }
}
