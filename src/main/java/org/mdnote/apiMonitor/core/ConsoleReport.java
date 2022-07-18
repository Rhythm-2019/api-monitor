package org.mdnote.apiMonitor.core;

import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.storage.IMetricStorage;
import org.mdnote.apiMonitor.viewer.ConsoleTerminal;

public class ConsoleReport extends ScheduleReporter {

    private IMetricStorage metricStorage;

    private Aggregator aggregator;

    public ConsoleReport(IMetricStorage metricStorage, Aggregator aggregator) {
        super(metricStorage, aggregator, new ConsoleTerminal());
    }
}
