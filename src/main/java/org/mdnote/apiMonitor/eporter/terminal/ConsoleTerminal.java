package org.mdnote.apiMonitor.eporter.terminal;

import org.mdnote.apiMonitor.aggregator.AggregateResult;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
public class ConsoleTerminal extends Terminal {

    @Override
    public void output(AggregateResult aggregateResult) {
        if (this.formatter == null) {
            return;
        }
        System.out.println(this.formatter.format(aggregateResult));
    }

}
