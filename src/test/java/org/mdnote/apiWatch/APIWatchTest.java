package org.mdnote.apiWatch;

import org.junit.Test;
import org.mdnote.apiWatch.aggregator.Aggregator;
import org.mdnote.apiWatch.aggregator.calculator.*;
import org.mdnote.apiWatch.collecter.DefaultMetricCollector;
import org.mdnote.apiWatch.collecter.IMetricCollector;
import org.mdnote.apiWatch.report.ConsoleReport;
import org.mdnote.apiWatch.report.Report;
import org.mdnote.apiWatch.storage.IMetricStorage;
import org.mdnote.apiWatch.storage.RedisMetricStorage;

public class APIWatchTest {

    @Test
    public void test() throws InterruptedException {
        IMetricStorage metricStorage = new RedisMetricStorage("192.168.80.154", 6379, "");
        IMetricCollector metricCollector = new DefaultMetricCollector(metricStorage);

        for (int i = 0; i < 10; i++) {
            Metric metric = new Metric();
            metric.setApiName("test" + i);
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

        Report consoleReport = new ConsoleReport(metricStorage, aggregator);

        consoleReport.start(60, 10);
    }

    private Metric buildMetric() {

        return null;
    }
}
