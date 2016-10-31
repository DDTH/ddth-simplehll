package com.github.ddth.hll.qnd.test;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.PjHLL;
import com.github.ddth.hll.utils.HLLUtils;

import junit.framework.TestCase;

public class TestPjHLL extends TestCase {

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

    public void testPjEmpty() {
        IHLL hll = new PjHLL().init();

        assertEquals(0, hll.count());
    }

    public void testPjEmptySerDer() throws Exception {
        IHLL hll = new PjHLL().init();

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof PjHLL);

        assertFieldEquals(PjHLL.class, "numRegisterIndexBits", hll, der);
        assertFieldEquals(PjHLL.class, "numHashBits", hll, der);

        Object innerThis = reflectionGetValue(PjHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(PjHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }

    public void testPjOne() {
        IHLL hll = new PjHLL().init();
        hll.add(System.currentTimeMillis());

        assertEquals(1, hll.count());
    }

    public void testPjOneSerDer() throws Exception {
        IHLL hll = new PjHLL().init();
        hll.add(System.currentTimeMillis());

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof PjHLL);

        assertFieldEquals(PjHLL.class, "numRegisterIndexBits", hll, der);
        assertFieldEquals(PjHLL.class, "numHashBits", hll, der);

        Object innerThis = reflectionGetValue(PjHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(PjHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }
}
