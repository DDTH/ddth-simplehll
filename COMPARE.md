`<HLL Lib> <num items> <error rate %> <serialized size> <time ms>`

PjHLL needs minimal space to store serialized data of HLL counter.
AtsHLL is the fastest library, but also need the largest space to store the serialized data.
AkHLL has the worst union accuracy.


`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=1000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 29586  ===============
AkHLL : 29667   0.27    32771   141.271
AtsHLL: 29652   0.22    43700   84.996
PjHLL : 29833   0.83    20487   145.724
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=10000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 259255 ===============
AkHLL : 282888  8.35    32771   838.407
AtsHLL: 259738  0.19    43700   217.576
PjHLL : 260917  0.64    20487   503.592
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=20000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 450847 ===============
AkHLL : 517610  12.90   32771   575.952
AtsHLL: 450710  0.03    43700   193.862
PjHLL : 450623  0.05    20487   466.054
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=40000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 699198 ===============
AkHLL : 1017876 31.31   32771   1024.275
AtsHLL: 701463  0.32    43700   309.836
PjHLL : 707057  1.11    20487   873.103
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=80000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 908881 ===============
AkHLL : 1859647 51.13   32771   773.345
AtsHLL: 913305  0.48    43700   334.212
PjHLL : 903114  0.64    20487   938.714
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=160000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 991821 ===============
AkHLL : 3641817 72.77   32771   1834.379
AtsHLL: 997457  0.57    43700   584.584
PjHLL : 1001293 0.95    20487   2454.132
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=320000 -DmaxItemValue=1000000 -DnumDataSets=30`
```
===== Final: 999929 ===============
AkHLL : 6792099 85.28   32771   2339.570
AtsHLL: 1005565 0.56    43700   897.854
PjHLL : 995147  0.48    20487   2898.056
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=10000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 295546 ===============
AkHLL : 295623  0.03    32771   572.620
AtsHLL: 295166  0.13    43700   191.466
PjHLL : 296392  0.29    20487   383.974
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=20000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 582058 ===============
AkHLL : 593953  2.00    32771   552.092
AtsHLL: 584507  0.42    43700   214.880
PjHLL : 581699  0.06    20487   475.775
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=40000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 1131087    ===============
AkHLL : 1172853 3.56    32771   655.441
AtsHLL: 1132581 0.13    43700   252.142
PjHLL : 1143187 1.06    20487   724.148
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=80000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 2134030    ===============
AkHLL : 2345831 9.03    32771   990.241
AtsHLL: 2132859 0.05    43700   343.431
PjHLL : 2154627 0.96    20488   833.805
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=160000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 3812059    ===============
AkHLL : 4652690 18.07   32771   1343.455
AtsHLL: 3802670 0.25    43700   550.629
PjHLL : 3772128 1.06    20488   4222.683
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=320000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 6169169    ===============
AkHLL : 9305264 33.70   32771   5815.658
AtsHLL: 6167566 0.03    43700   1054.139
PjHLL : 6203898 0.56    20488   3311.328
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=640000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 8534739    ===============
AkHLL : 18589419    54.09   32771   7321.963
AtsHLL: 8530237     0.05    43700   1611.035
PjHLL : 8591363     0.66    20488   11046.714
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=1280000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 9785282    ===============
AkHLL : 36643450    73.30   32771   17864.506
AtsHLL: 9811664     0.27    43700   3562.639
PjHLL : 9775159     0.10    20488   11168.748
```

`mvn package exec:java -Dexec.mainClass="com.github.ddth.hll.qnd.QndCompare" -Dexec.classpathScope=test -DnumItems=2560000 -DmaxItemValue=10000000 -DnumDataSets=30`
```
===== Final: 9995484    ===============
AkHLL : 69816813    85.68   32771   23729.962
AtsHLL: 10005012    0.10    43700   6904.365
PjHLL : 9983429     0.12    20488   27266.392
```
