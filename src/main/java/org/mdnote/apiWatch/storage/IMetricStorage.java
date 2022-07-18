package org.mdnote.apiWatch.storage;

import java.util.List;
import java.util.Map;

import org.mdnote.apiWatch.Metric;

public interface IMetricStorage {
    
    void saveMetric(Metric metric) throws MetricStorageException;

    Map<String, List<Metric>> getMetric(long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;

    List<Metric> getMetric(String apiName, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException;
}
