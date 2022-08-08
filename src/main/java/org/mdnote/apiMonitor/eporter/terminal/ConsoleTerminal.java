package org.mdnote.apiMonitor.eporter.terminal;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.eporter.formatter.Formatter;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 命令行输出
 */
public class ConsoleTerminal extends Terminal {

    public ConsoleTerminal(Formatter formatter) {
        super(formatter);
    }

    @Override
    public void output(List<AggregateResult> aggregateResultList) {
        aggregateResultList.forEach(aggregateResult -> {
            if (this.formatter == null) {
                return;
            }
            System.out.println(this.formatter.format(aggregateResult));
        });
    }
}
