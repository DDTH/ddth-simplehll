package com.github.ddth.hll.qnd;

import com.github.ddth.hll.AtsHLL;
import com.github.ddth.hll.IHLL;

public class QndAtsHLL extends BaseQnd {

    protected IHLL hyperLogLog() {
        return new AtsHLL().init();
    }

    public static void main(String[] args) throws Exception {
        QndAtsHLL qnd = new QndAtsHLL();
        qnd.run();
    }

}
