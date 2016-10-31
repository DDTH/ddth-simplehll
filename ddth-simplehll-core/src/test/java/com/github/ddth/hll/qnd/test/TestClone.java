package com.github.ddth.hll.qnd.test;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AkHLL;
import com.github.ddth.hll.impl.AtsHLL;
import com.github.ddth.hll.impl.PjHLL;
import com.google.common.hash.HashFunction;

import junit.framework.TestCase;

public class TestClone extends TestCase {

    private static Object reflectionGetValue(Class<?> clazz, String fieldName, Object obj)
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    @SuppressWarnings("unchecked")
    private static <T> T reflectionGetValue(Class<?> clazz, String fieldName, Object obj,
            Class<T> fieldClass) throws Exception {
        Object field = reflectionGetValue(clazz, fieldName, obj);
        return (T) field;
    }

    private static void assertFieldEquals(Class<?> clazz, String fieldName, Object obj1,
            Object obj2) throws Exception {
        Object thisField = reflectionGetValue(clazz, fieldName, obj1);
        Object thatField = reflectionGetValue(clazz, fieldName, obj2);
        assertEquals(thisField, thatField);
    }

    public void testAkClone() throws Exception {
        IHLL hll = new AkHLL().init();
        IHLL clone = hll.clone();
        assertTrue(clone instanceof AkHLL);
        assertFieldEquals(AkHLL.class, "log2m", hll, clone);
        assertFieldEquals(AkHLL.class, "regWidth", hll, clone);

        HashFunction hfThis = reflectionGetValue(AkHLL.class, "hf", hll, HashFunction.class);
        HashFunction hfThat = reflectionGetValue(AkHLL.class, "hf", clone, HashFunction.class);
        assertEquals(hfThis.bits(), hfThat.bits());
        assertTrue(hfThis != hfThat);

        Object innerThis = reflectionGetValue(AkHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AkHLL.class, "hll", clone);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), clone.toBytes()));
    }

    public void testAtsClone() throws Exception {
        IHLL hll = new AtsHLL().init();
        IHLL clone = hll.clone();
        assertTrue(clone instanceof AtsHLL);
        assertFieldEquals(AtsHLL.class, "log2m", hll, clone);

        Object innerThis = reflectionGetValue(AtsHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AtsHLL.class, "hll", clone);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), clone.toBytes()));
    }

    public void testPjClone() throws Exception {
        IHLL hll = new PjHLL().init();
        IHLL clone = hll.clone();
        assertTrue(clone instanceof PjHLL);
        assertFieldEquals(PjHLL.class, "numRegisterIndexBits", hll, clone);
        assertFieldEquals(PjHLL.class, "numHashBits", hll, clone);

        Object innerThis = reflectionGetValue(PjHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(PjHLL.class, "hll", clone);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), clone.toBytes()));
    }
}
