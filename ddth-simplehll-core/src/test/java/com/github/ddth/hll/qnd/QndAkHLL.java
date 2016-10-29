package com.github.ddth.hll.qnd;

import com.github.ddth.hll.AkHLL;
import com.github.ddth.hll.IHLL;

public class QndAkHLL extends BaseQnd {

    protected IHLL hyperLogLog() {
        return new AkHLL().init();
    }

    public static void main(String[] args) throws Exception {
        QndAkHLL qnd = new QndAkHLL();
        qnd.run();
    }

}
