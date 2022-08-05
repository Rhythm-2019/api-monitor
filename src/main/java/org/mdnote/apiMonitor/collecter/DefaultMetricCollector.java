package org.mdnote.apiMonitor.collecter;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.storage.MetricStorage;
import org.mdnote.apiMonitor.exception.MetricStorageException;

import java.util.List;


/**
 * @author Rhythm-2019
 *
 * IMetricCollector 的默认实现，支持同步记录和异步记录
 */
@Slf4j
public class DefaultMetricCollector implements SyncMetricCollector {

    /**
     * 指标存储接口
     */
    private List<MetricStorage> metricStorageList;

    /**
     * 构造方法
     * @param metricStorageList 指标存储
     */
    public DefaultMetricCollector(List<MetricStorage> metricStorageList) {
        this.metricStorageList = metricStorageList;
    }

    @Override
    public void mark(ClientMetric metric) {
        this.metricStorageList.forEach(metricStorage -> {
            try {
                metricStorage.saveMetric(metric);
            } catch (MetricStorageException e) {
                log.error("mark client metric failed, error is {}", e.getMessage());
            }
        });
    }

    @Override
    public void mark(ServerMetric metric) {
        this.metricStorageList.forEach(metricStorage -> {
            try {
                metricStorage.saveMetric(metric);
            } catch (MetricStorageException e) {
                log.error("mark metric failed, error is {}", e.getMessage());
            }
        });
    }
}
