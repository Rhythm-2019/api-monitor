package org.mdnote.apiMonitor.storage;

import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

import java.util.List;
import java.util.Map;

/**
 * @author Rhythm-2019
 *
 * 指标存储的抽象接口
 */
public interface MetricStorage {

    /**
     * 保存指标
     * @param metric 指标
     * @throws MetricStorageException 存储异常，在连接异常、内存溢出的情况下爆出
     */
    void saveMetric(ClientMetric metric) throws MetricStorageException;
    void saveMetric(ServerMetric metric) throws MetricStorageException;

    /**
     * 获取客户端相关指标数据
     */
    List<ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    List<ServerMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;
}
