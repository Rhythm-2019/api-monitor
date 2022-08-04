package org.mdnote.apiMonitor.collecter;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.storage.IMetricStorage;
import org.mdnote.apiMonitor.storage.MetricStorageException;

import java.util.concurrent.Executors;


/**
 * @author Rhythm-2019
 *
 * IMetricCollector 的默认实现，支持同步记录和异步记录
 */
@Slf4j
public class DefaultMetricCollector implements IMetricCollector {

    /**
     * 指标存储接口
     */
    private IMetricStorage metricStorage;

    /**
     * guava 异步是俺
     */
    private EventBus eventBus;

    /**
     * 构造方法
     * @param metricStorage 指标存储
     * @param nThreads 异步线程数
     */
    public DefaultMetricCollector(IMetricStorage metricStorage, int nThreads) {
        this.metricStorage = metricStorage;
        this.eventBus = new AsyncEventBus(Executors.newFixedThreadPool(nThreads));
        this.eventBus.register(new AsyncMarkMetric());
    }

    @Override
    public void mark(Metric metric) {
        try {
            this.metricStorage.saveMetric(metric);
        } catch (MetricStorageException e) {
            log.error("mark metric failed, error is {}", e.getMessage());
        }
    }

    @Override
    public void markAsync(Metric metric) {
        this.eventBus.post(metric);
    }

    private class AsyncMarkMetric {

        @Subscribe
        public void mark(Metric metric) {
            try {
                metricStorage.saveMetric(metric);
            } catch (MetricStorageException e) {
                log.error("mark metric failed, error is {}", e.getMessage());
            }
        }
    }
}
