package org.mdnote.apiWatch.collecter;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiWatch.Metric;
import org.mdnote.apiWatch.storage.IMetricStorage;
import org.mdnote.apiWatch.storage.MetricStorageException;

@Slf4j
public class DefaultMetricCollector implements IMetricCollector {

    private IMetricStorage metricStorage;

    public DefaultMetricCollector(IMetricStorage metricStorage) {
        this.metricStorage = metricStorage;
    }

    public void mark(Metric metric) {
        try {
            this.metricStorage.saveMetric(metric);
        } catch (MetricStorageException e) {
            log.error("mark metric failed, error is {}", e.getMessage());
        }
    }

}
