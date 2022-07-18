package org.mdnote.apiWatch.collecter;

import org.mdnote.apiWatch.Metric;

public interface IMetricCollector {

    void mark(Metric metric);
}