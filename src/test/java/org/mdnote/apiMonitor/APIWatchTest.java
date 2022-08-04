package org.mdnote.apiMonitor;

import org.junit.Test;
import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.aggregator.calculator.*;
import org.mdnote.apiMonitor.collecter.DefaultMetricCollector;
import org.mdnote.apiMonitor.collecter.IMetricCollector;
import org.mdnote.apiMonitor.core.ConsoleReport;
import org.mdnote.apiMonitor.core.ScheduleReporter;
import org.mdnote.apiMonitor.storage.IMetricStorage;
import org.mdnote.apiMonitor.storage.RedisMetricStorage;

public class APIWatchTest {

    @Test
    public void test() throws InterruptedException {
        IMetricStorage metricStorage = new RedisMetricStorage("192.168.80.154", 6379, "");
        IMetricCollector metricCollector = new DefaultMetricCollector(metricStorage, 10);

        for (int i = 0; i < 10; i++) {
            Metric metric = new Metric();
            metric.setUri("test" + i);
            long start = System.currentTimeMillis();
            metric.setCurrentTimestamp(start);

            Thread.sleep(1_000);

            metric.setResponseTime(System.currentTimeMillis() - start);
            metricCollector.mark(metric);
        }

        Aggregator aggregator = new Aggregator();
        aggregator.addCalculator(new MaxRespCalculator());
        aggregator.addCalculator(new MinRespCalculator());
        aggregator.addCalculator(new AvgRespCalculator());
        aggregator.addCalculator(new P99CalculatorImpl());
        aggregator.addCalculator(new P999CalculatorImpl());
        aggregator.addCalculator(new TpsCalculatorImpl());

        ScheduleReporter consoleReport = new ConsoleReport(metricStorage, aggregator);

        consoleReport.start(60, 10);
    }

    private Metric buildMetric() {

        return null;
    }
}
