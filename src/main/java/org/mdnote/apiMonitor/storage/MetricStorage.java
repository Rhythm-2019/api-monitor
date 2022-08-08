package org.mdnote.apiMonitor.storage;

import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.Metric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.metric.annotation.MetricField;
import org.mdnote.apiMonitor.metric.annotation.Timestamp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rhythm-2019
 * <p>
 * 指标存储的抽象类
 */
public abstract class MetricStorage {

    /**
     * 保存客户端指标
     *
     * @param metric 指标
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    public abstract void saveMetric(ClientMetric metric) throws MetricStorageException;

    /**
     * 保存服务端指标
     *
     * @param metric 指标
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    public abstract void saveMetric(ServerMetric metric) throws MetricStorageException;

    /**
     * 获取客户端相关指标数据
     *
     * @param serverName        服务名称
     * @param uri               uri
     * @param startTimeInMillis 开始时间
     * @param endTimeInMillis   结束时间
     * @return 客户端指标数据
     * @throws MetricStorageException 存储异常
     */
    public abstract List<ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    /**
     * 获取服务端相关指标数据
     *
     * @param serverName        服务名称
     * @param uri               uri
     * @param startTimeInMillis 开始时间
     * @param endTimeInMillis   结束时间
     * @return 服务端指标数据
     * @throws MetricStorageException 存储异常
     */
    public abstract List<ServerMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    /**
     * 获取指标的时间戳字段名
     *
     * @param metric 指标
     * @return 指标时间戳字段名
     */
    protected String getTimestampFieldName(Metric metric) {
        List<String> tsFields = Arrays.stream(metric.getClass().getFields())
                .filter(field -> field.getAnnotation(Timestamp.class) == null)
                .map(Field::getName)
                .collect(Collectors.toList());
        if (tsFields.size() == 0) {
            return null;
        }
        return tsFields.get(0);
    }

    /**
     * 获取指标标识名和字段的映射
     *
     * @param clz 类型
     * @return 指标标识名和字段的映射
     */
    protected Map<String, Field> getFieldMap(Class<?> clz) {
        return Arrays.stream(clz.getFields())
                .filter(field -> field.getAnnotation(MetricField.class) != null)
                .collect(Collectors.toMap(field -> field.getAnnotation(MetricField.class).value(), field -> field));
    }

}
