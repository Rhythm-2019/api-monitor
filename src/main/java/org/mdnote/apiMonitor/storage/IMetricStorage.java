package org.mdnote.apiMonitor.storage;

import java.util.List;
import java.util.Map;

import org.mdnote.apiMonitor.Metric;

/**
 * @author Rhythm-2019
 *
 * 指标存储的抽象接口
 */
public interface IMetricStorage {

    /**
     * 保存指标
     * @param metric 指标
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    void saveMetric(Metric metric) throws MetricStorageException;

    /**
     * 获取 (@param startTimeInMillis) 到 (@param endTimeInMillis) 的所有指标值
     * @param startTimeInMillis 开始时间 ms
     * @param endTimeInMillis 结束时间 ms
     * @return API名称-指标列表映射
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    Map<String, List<Metric>> getMetric(long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    /**
     * 获取单个 API @(param apiName) 从 (@param startTimeInMillis) 到 (@param endTimeInMillis) 的所有指标值
     * @param apiName API名称
     * @param startTimeInMillis 开始时间 ms
     * @param endTimeInMillis 结束时间 ms
     * @return 指标列表
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    List<Metric> getMetric(String apiName, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;
}
