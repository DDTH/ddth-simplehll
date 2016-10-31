package com.github.ddth.hll.qnd;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AkHLL;

public class QndClone {
    public static void main(String[] args) {
        IHLL hll = new AkHLL().init();
        IHLL another = hll.clone();
        System.out.println(hll);
        System.out.println(another);
    }
}
