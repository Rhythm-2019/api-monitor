package org.mdnote.apiMonitor.viewer;

import org.mdnote.apiMonitor.AggregateResult;

import java.util.List;

/**
 * @author Rhythm-2019
 *
 * ITerminal 对所有输出的终端进行抽象
 *
 * API、Email 等终端输出都可以实现该接口
 */
public interface ITerminal {
    void output(List<AggregateResult> aggregateResultMap);
}
