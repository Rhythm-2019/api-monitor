package org.mdnote.apiMonitor.reporter.terminal;

import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.reporter.formatter.Formatter;

import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 终端抽象类，用于聚合结果的输出
 */
public abstract class Terminal {

    protected Formatter formatter;

    public Terminal(Formatter formatter) {
        this.formatter = formatter;
    }

    public abstract void output(List<AggregateResult> aggregateResultList);

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
}
