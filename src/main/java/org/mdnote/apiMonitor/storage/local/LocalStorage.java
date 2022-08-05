package org.mdnote.apiMonitor.storage.local;

import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.storage.MetricStorage;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 本地存储，使用跳跃表实现
 */
public class LocalStorage implements MetricStorage {

    private static final int MEMORY_CLEAR_DURATION_MIN = 30;
    private LocalMetricHolder<ClientMetric> clientMetricHolder;
    private LocalMetricHolder<ServerMetric> serverMetricHolder;


    public LocalStorage() {
        this.clientMetricHolder = new LocalMetricHolder<>(MEMORY_CLEAR_DURATION_MIN, TimeUnit.MINUTES);
        this.serverMetricHolder = new LocalMetricHolder<>(MEMORY_CLEAR_DURATION_MIN, TimeUnit.MINUTES);
    }

    @Override
    public void saveMetric(ClientMetric metric) throws MetricStorageException {
        clientMetricHolder.put(metric);
    }

    @Override
    public void saveMetric(ServerMetric metric) throws MetricStorageException {
        serverMetricHolder.put(metric);
    }

    @Override
    public List<ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return this.clientMetricHolder.list(serverName, uri, startTimeInMillis, endTimeInMillis);
    }

    @Override
    public List<ServerMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return this.serverMetricHolder.list(serverName, uri, startTimeInMillis, endTimeInMillis);
    }
}
