package com.github.ddth.hll.qnd;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.PjHLL;

public class QndPjHLL extends BaseQnd {

    protected IHLL hyperLogLog() {
        return new PjHLL().init();
    }

    public static void main(String[] args) throws Exception {
        QndPjHLL qnd = new QndPjHLL();
        qnd.run();
    }
}
