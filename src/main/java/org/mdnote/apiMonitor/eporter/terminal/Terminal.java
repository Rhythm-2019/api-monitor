package org.mdnote.apiMonitor.eporter.terminal;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.eporter.formatter.Formatter;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
public abstract class Terminal {

    protected Formatter formatter;

    abstract void output(AggregateResult aggregateResult);

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
}
