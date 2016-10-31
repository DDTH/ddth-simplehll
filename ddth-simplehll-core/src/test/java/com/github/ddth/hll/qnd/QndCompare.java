package com.github.ddth.hll.qnd;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AkHLL;
import com.github.ddth.hll.impl.AtsHLL;
import com.github.ddth.hll.impl.PjHLL;

public class QndCompare {

    private static IHLL akHLL() {
        return new AkHLL().init();
    }

    private static IHLL atsHLL() {
        return new AtsHLL().init();
    }

    private static IHLL pjHLL() {
        return new PjHLL().init();
    }

    private static double calcError(long numItems, long expectedNumItems) {
        double error = 100.0 * (numItems - expectedNumItems);
        error /= numItems;
        return error >= 0.0 ? error : -error;
    }

    public void run(final int NUM_ITEMS, final int MAX_ITEM_VALUE, final int NUM_DATASETS) {
        final Random rand = new Random(System.currentTimeMillis());
        final Set<Integer> dataSet = new HashSet<>();

        final IHLL akHLL = akHLL();
        final IHLL atsHLL = atsHLL();
        final IHLL pjHLL = pjHLL();

        long timeAk = 0, timeAts = 0, timePj = 0;

        for (int i = 0; i < NUM_DATASETS; i++) {
            final Set<Integer> localDataSet = new HashSet<>();
            final IHLL localAkHLL = akHLL();
            final IHLL localAtsHLL = atsHLL();
            final IHLL localPjHLL = pjHLL();

            for (int j = 0; j < NUM_ITEMS; j++) {
                int value = rand.nextInt(MAX_ITEM_VALUE);
                localDataSet.add(value);
                dataSet.add(value);

                byte[] data = String.valueOf(value).getBytes();
                {
                    long t = System.nanoTime();
                    localAkHLL.add(data);
                    timeAk += (System.nanoTime() - t);
                }
                {
                    long t = System.nanoTime();
                    localAtsHLL.add(data);
                    timeAts += (System.nanoTime() - t);
                }
                {
                    long t = System.nanoTime();
                    localPjHLL.add(data);
                    timePj += (System.nanoTime() - t);
                }
            }

            long numItems = localDataSet.size();
            System.out.println(String.format("===== Data Set %d: %d =====", i + 1, numItems));
            {
                long t = System.nanoTime();
                long hllSize = localAkHLL.count();
                double error = calcError(hllSize, numItems);
                System.out.println(String.format("AkHLL : %d\t%.2f\t%d", hllSize, error,
                        localAkHLL.toBytes().length));
                akHLL.merge(localAkHLL);
                timeAk += (System.nanoTime() - t);
            }
            {
                long t = System.nanoTime();
                long hllSize = localAtsHLL.count();
                double error = calcError(hllSize, numItems);
                System.out.println(String.format("AtsHLL: %d\t%.2f\t%d", hllSize, error,
                        localAtsHLL.toBytes().length));
                atsHLL.merge(localAtsHLL);
                timeAts += (System.nanoTime() - t);
            }
            {
                long t = System.nanoTime();
                long hllSize = localPjHLL.count();
                double error = calcError(hllSize, numItems);
                System.out.println(String.format("PjHLL : %d\t%.2f\t%d", hllSize, error,
                        localPjHLL.toBytes().length));
                pjHLL.merge(localPjHLL);
                timePj += (System.nanoTime() - t);
            }
        }

        long totalNumItems = dataSet.size();
        System.out.println(String.format("===== Final: %d\t===============", totalNumItems));
        {
            long hllSize = akHLL.count();
            double error = calcError(hllSize, totalNumItems);
            System.out.println(String.format("AkHLL : %d\t%.2f\t%d\t%.3f", hllSize, error,
                    akHLL.toBytes().length, timeAk / 1E6));
        }
        {
            long hllSize = atsHLL.count();
            double error = calcError(hllSize, totalNumItems);
            System.out.println(String.format("AtsHLL: %d\t%.2f\t%d\t%.3f", hllSize, error,
                    atsHLL.toBytes().length, timeAts / 1E6));
        }
        {
            long hllSize = pjHLL.count();
            double error = calcError(hllSize, totalNumItems);
            System.out.println(String.format("PjHLL : %d\t%.2f\t%d\t%.3f", hllSize, error,
                    pjHLL.toBytes().length, timePj / 1E6));
        }
    }

    /*
     * clear && mvn package exec:java
     * -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare"
     * -Dexec.classpathScope=test -DnumItems=100000 -DmaxItemValue=1000000
     * -DnumDataSets=4
     */

    public static void main(String[] args) {
        int numItems, maxItemValue, numDataSets;
        try {
            numItems = Integer.parseInt(System.getProperty("numItems", "100000"));
        } catch (Exception e) {
            numItems = 100000;
        }
        try {
            maxItemValue = Integer.parseInt(System.getProperty("maxItemValue", "1000000"));
        } catch (Exception e) {
            maxItemValue = 1000000;
        }
        try {
            numDataSets = Integer.parseInt(System.getProperty("numDataSets", "10"));
        } catch (Exception e) {
            numDataSets = 10;
        }

        QndCompare qnd = new QndCompare();
        qnd.run(numItems, maxItemValue, numDataSets);
    }
}
