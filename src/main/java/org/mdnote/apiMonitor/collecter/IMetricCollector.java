package org.mdnote.apiMonitor.collecter;

import org.mdnote.apiMonitor.Metric;

public interface IMetricCollector {
    void mark(Metric metric);

    void markAsync(Metric metric);
}