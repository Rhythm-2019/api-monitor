package org.mdnote.apiMonitor.collecter;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.storage.MetricStorage;

import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * @author Rhythm-2019
 * <p>
 * IMetricCollector 的默认实现，支持同步记录和异步记录
 */
@Slf4j
public class DefaultAsyncMetricCollector implements AsyncMetricCollector {

    /**
     * 指标存储接口
     */
    private List<MetricStorage> metricStorageList;

    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 构造方法
     *
     * @param metricStorageList 指标存储
     * @param executorService   线程池
     */
    public DefaultAsyncMetricCollector(List<MetricStorage> metricStorageList, ExecutorService executorService) {
        this.metricStorageList = metricStorageList;
        this.executorService = executorService;
    }

    @Override
    public void asyncMark(ClientMetric metric) {
        this.executorService.submit(() -> {
            this.metricStorageList.forEach(metricStorage -> {
                try {
                    metricStorage.saveMetric(metric);
                } catch (MetricStorageException e) {
                    log.error("mark client metric failed, error is {}", e.getMessage());
                }
            });

        });
    }

    @Override
    public void asyncMark(ServerMetric metric) {
        this.executorService.submit(() -> {
            this.metricStorageList.forEach(metricStorage -> {
                try {
                    metricStorage.saveMetric(metric);
                } catch (MetricStorageException e) {
                    log.error("mark metric failed, error is {}", e.getMessage());
                }
            });
        });
    }
}
