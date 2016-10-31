package com.github.ddth.hll.qnd;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AkHLL;

public class QndAkHLL extends BaseQnd {

    protected IHLL hyperLogLog() {
        return new AkHLL().init();
    }

    public static void main(String[] args) throws Exception {
        QndAkHLL qnd = new QndAkHLL();
        qnd.run();
    }

}
