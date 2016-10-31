package com.github.ddth.hll.qnd.test;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.impl.AkHLL;
import com.github.ddth.hll.utils.HLLUtils;
import com.google.common.hash.HashFunction;

import junit.framework.TestCase;

public class TestAkHLL extends TestCase {

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

    public void testAkEmpty() {
        IHLL hll = new AkHLL().init();

        assertEquals(0, hll.count());
    }

    public void testAkEmptySerDer() throws Exception {
        IHLL hll = new AkHLL().init();

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof AkHLL);

        assertFieldEquals(AkHLL.class, "log2m", hll, der);
        assertFieldEquals(AkHLL.class, "regWidth", hll, der);

        HashFunction hfThis = reflectionGetValue(AkHLL.class, "hf", hll, HashFunction.class);
        HashFunction hfThat = reflectionGetValue(AkHLL.class, "hf", der, HashFunction.class);
        assertEquals(hfThis.bits(), hfThat.bits());
        assertTrue(hfThis != hfThat);

        Object innerThis = reflectionGetValue(AkHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AkHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }

    public void testAkOne() {
        IHLL hll = new AkHLL().init();
        hll.add(System.currentTimeMillis());

        assertEquals(1, hll.count());
    }

    public void testAkOneSerDer() throws Exception {
        IHLL hll = new AkHLL().init();
        hll.add(System.currentTimeMillis());

        byte[] data = HLLUtils.toBytes(hll);
        assertNotNull(data);
        IHLL der = HLLUtils.fromBytes(data);
        assertTrue(der instanceof AkHLL);

        assertFieldEquals(AkHLL.class, "log2m", hll, der);
        assertFieldEquals(AkHLL.class, "regWidth", hll, der);

        HashFunction hfThis = reflectionGetValue(AkHLL.class, "hf", hll, HashFunction.class);
        HashFunction hfThat = reflectionGetValue(AkHLL.class, "hf", der, HashFunction.class);
        assertEquals(hfThis.bits(), hfThat.bits());
        assertTrue(hfThis != hfThat);

        Object innerThis = reflectionGetValue(AkHLL.class, "hll", hll);
        Object innerThat = reflectionGetValue(AkHLL.class, "hll", der);
        assertTrue(innerThis != innerThat);

        assertTrue(Arrays.equals(hll.toBytes(), der.toBytes()));
    }
}
