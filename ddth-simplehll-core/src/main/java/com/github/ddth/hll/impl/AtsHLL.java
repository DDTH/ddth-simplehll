package com.github.ddth.hll.impl;

import java.io.IOException;
import java.lang.reflect.Field;

import com.clearspring.analytics.stream.cardinality.CardinalityMergeException;
import com.clearspring.analytics.stream.cardinality.HyperLogLog;
import com.github.ddth.hll.IHLL;

/**
 * This {@link IHLL} utilizes AddThis Stream's library as the underlying
 * HyperLogLog implementation.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 * @see https://github.com/addthis/stream-lib
 */
public class AtsHLL implements IHLL {

    private int log2m = 16;
    private HyperLogLog hll;

    public AtsHLL() {
    }

    public AtsHLL(int log2m) {
        this.log2m = log2m;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AtsHLL clone() {
        try {
            AtsHLL clone = (AtsHLL) super.clone();
            if (hll != null) {
                if (hll != null) {
                    clone.init(this.toBytes());
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the "log2m" value (the number of bits to use as the basis for the
     * HLL instance). The larger the log2m the better the accuracy but also the
     * higher storage required.
     * 
     * @return
     */
    public int getLog2m() {
        return log2m;
    }

    /**
     * Sets the "log2m" value (the number of bits to use as the basis for the
     * HLL instance). The larger the log2m the better the accuracy but also the
     * higher storage required.
     * 
     * @param log2m
     * @return
     */
    public AtsHLL setLog2m(int log2m) {
        this.log2m = log2m;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] toBytes() {
        if (hll == null) {
            throw new IllegalStateException();
        }
        try {
            return hll.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL init() {
        hll = new HyperLogLog(log2m);
        return this;
    }

    private static int reflectGetLog2m(HyperLogLog hll) {
        try {
            Field field = HyperLogLog.class.getDeclaredField("log2m");
            field.setAccessible(true);
            Object value = field.get(hll);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (NoSuchFieldException e) {
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL init(byte[] data) {
        try {
            hll = HyperLogLog.Builder.build(data);
            this.log2m = reflectGetLog2m(hll);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        if (hll == null) {
            throw new IllegalStateException();
        }
        return hll.cardinality();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL add(Object obj) {
        if (hll == null) {
            throw new IllegalStateException();
        }
        hll.offer(obj);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL merge(IHLL hll) {
        if (this.hll == null) {
            throw new IllegalStateException();
        }
        if (!(hll instanceof AtsHLL)) {
            throw new IllegalArgumentException("Argument is not of type [" + AtsHLL.class + "]!");
        }
        HyperLogLog other = ((AtsHLL) hll).hll;
        if (other == null) {
            throw new IllegalArgumentException("The supplied object has not been initialized!");
        }
        try {
            this.hll.addAll(other);
        } catch (CardinalityMergeException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

}
