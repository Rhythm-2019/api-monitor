package org.mdnote.apiWatch.report;

import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.aggregator.Aggregator;
import org.mdnote.apiWatch.storage.IMetricStorage;

public class ConsoleReport extends Report {

    private IMetricStorage metricStorage;

    private Aggregator aggregator;

    public ConsoleReport(IMetricStorage metricStorage, Aggregator aggregator) {
        super(metricStorage, aggregator);
    }

    @Override
    public void report(AggregateResult aggregateResult) {
        System.out.println(aggregateResult.toString());
    }
}
