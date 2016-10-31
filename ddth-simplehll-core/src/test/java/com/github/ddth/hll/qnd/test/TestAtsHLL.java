package com.github.ddth.hll.qnd.test;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AtsHLL;
import com.github.ddth.hll.utils.HLLUtils;

import junit.framework.TestCase;

public class TestAtsHLL extends TestCase {

    private static Object reflectionGetValue(Class<?> clazz, String fieldName, Object obj)
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private static void assertFieldEquals(Class<?> clazz, String fieldName, Object obj1,
            Object obj2) throws Exception {
        Object thisField = reflectionGetValue(clazz, fieldName, obj1);
        Object thatField = reflectionGetValue(clazz, fieldName, obj2);
        assertEquals(thisField, thatField);
    }

    public void testAtsEmpty() {
        IHLL hll = new AtsHLL().init();

        assertEquals(0, hll.count());
    }

    public void testAtsEmptySerDer() throws Exception {
        IHLL hll = new AtsHLL().init();

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof AtsHLL);

        assertFieldEquals(AtsHLL.class, "log2m", hll, der);

        Object innerThis = reflectionGetValue(AtsHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AtsHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }

    public void testAtsOne() {
        IHLL hll = new AtsHLL().init();
        hll.add(System.currentTimeMillis());

        assertEquals(1, hll.count());
    }

    public void testAtsOneSerDer() throws Exception {
        IHLL hll = new AtsHLL().init();
        hll.add(System.currentTimeMillis());

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof AtsHLL);

        assertFieldEquals(AtsHLL.class, "log2m", hll, der);

        Object innerThis = reflectionGetValue(AtsHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AtsHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }
}
