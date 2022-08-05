package org.mdnote.apiMonitor.eporter.formatter;

import org.mdnote.apiMonitor.aggregator.AggregateResult;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
public interface Formatter {
    String format(AggregateResult aggregateResult);
}
