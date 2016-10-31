package com.github.ddth.hll.qnd.test;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.IHLLFactory;
import com.github.ddth.hll.impl.AkHLL;
import com.github.ddth.hll.impl.AkHLLFactory;
import com.github.ddth.hll.impl.AtsHLL;
import com.github.ddth.hll.impl.AtsHLLFactory;
import com.github.ddth.hll.impl.PjHLL;
import com.github.ddth.hll.impl.PjHLLFactory;

import junit.framework.TestCase;

public class TestFactory extends TestCase {
    public void testAkFactory() {
        IHLLFactory factory = new AkHLLFactory();
        IHLL hll = factory.createHLL();
        assertTrue(hll instanceof AkHLL);
    }

    public void testAtsFactory() {
        IHLLFactory factory = new AtsHLLFactory();
        IHLL hll = factory.createHLL();
        assertTrue(hll instanceof AtsHLL);
    }

    public void testPjFactory() {
        IHLLFactory factory = new PjHLLFactory();
        IHLL hll = factory.createHLL();
        assertTrue(hll instanceof PjHLL);
    }
}
