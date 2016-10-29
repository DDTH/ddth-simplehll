package com.github.ddth.hll.qnd;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.ddth.hll.IHLL;

public abstract class BaseQnd {
    protected abstract IHLL hyperLogLog();

    public void run() {
        final int NUM_ITEMS = 100000;
        final int MAX_ITEM_VALUE = 1000000;
        final int NUM_DATA_SET = 10;
        final long t = System.currentTimeMillis();

        Random rand = new Random(System.currentTimeMillis());
        Set<Integer> dataSet = new HashSet<>();

        IHLL hll = hyperLogLog();
        for (int i = 0; i < NUM_DATA_SET; i++) {
            long t1 = System.currentTimeMillis();
            Set<Integer> ds = new HashSet<>();
            IHLL h = hyperLogLog();
            for (int j = 0; j < NUM_ITEMS; j++) {
                int value = rand.nextInt(MAX_ITEM_VALUE);
                ds.add(value);
                dataSet.add(value);

                byte[] data = String.valueOf(value).getBytes();
                h.add(data);
            }

            System.out.println("===== Data Set [" + (i + 1) + "] =====");
            long numItems = ds.size();
            long hllSize = h.count();
            double error = 100.0 * (hllSize - numItems);
            error /= numItems;
            System.out.println(String.format("Num items: %d", numItems));
            System.out.println(
                    String.format("HLL size : %d\t%.2f\t%d", hllSize, error, h.toBytes().length));
            hll.merge(h);

            long t2 = System.currentTimeMillis();
            System.out.println("\t" + (t2 - t1));
        }

        System.out.println("===== Final =====");
        long totalNumItems = dataSet.size();
        long hllSize = hll.count();
        double error = 100.0 * (hllSize - totalNumItems);
        error /= totalNumItems;
        System.out.println(String.format("Num items: %d", totalNumItems));
        System.out.println(
                String.format("HLL size : %d\t%.2f\t%d", hllSize, error, hll.toBytes().length));
        System.out.println("\t" + (System.currentTimeMillis() - t));
    }

}
