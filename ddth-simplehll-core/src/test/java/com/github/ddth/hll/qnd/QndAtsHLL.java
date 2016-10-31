package com.github.ddth.hll.qnd;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AtsHLL;

public class QndAtsHLL extends BaseQnd {

    protected IHLL hyperLogLog() {
        return new AtsHLL().init();
    }

    public static void main(String[] args) throws Exception {
        QndAtsHLL qnd = new QndAtsHLL();
        qnd.run();
    }

}
