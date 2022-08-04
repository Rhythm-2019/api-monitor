package org.mdnote.apiMonitor.storage;

import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.Metric;
import org.mdnote.apiMonitor.metric.ServiceMetric;

import java.util.List;
import java.util.Map;

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
    void saveMetric(ClientMetric metric) throws MetricStorageException;
    void saveMetric(ServiceMetric metric) throws MetricStorageException;

    /**
     * 获取客户端相关指标数据
     * @param serverName
     * @param uri
     * @param startTimeInMillis
     * @param endTimeInMillis
     * @return
     * @throws MetricStorageException
     */
    Map<String, ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    Map<String, ServiceMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;
}
