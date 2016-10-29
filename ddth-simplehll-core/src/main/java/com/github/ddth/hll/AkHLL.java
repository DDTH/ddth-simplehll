package com.github.ddth.hll;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import net.agkn.hll.HLL;

/**
 * This {@link IHLL} utilizes AggregateKnowledge's library as the underlying
 * HyperLogLog implementation.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 * @see https://github.com/aggregateknowledge/java-hll
 */
public class AkHLL implements IHLL {

    private int log2m = 16, regWidth = 4;
    private HLL hll;
    private HashFunction hf = Hashing.murmur3_128((int) (System.currentTimeMillis() / 1000));

    public AkHLL() {
    }

    public AkHLL(int log2m, int regWidth) {
        if (log2m != 0) {
            this.log2m = log2m;
        }
        if (regWidth != 0) {
            this.regWidth = regWidth;
        }
    }

    /**
     * Gets the "log2m" value (m = the number of probabilistic HLL registers).
     * The larger the log2m the better the accuracy but also the higher storage
     * required.
     * 
     * @return
     */
    public int getLog2m() {
        return log2m;
    }

    /**
     * Sets the "log2m" value (m = the number of probabilistic HLL registers).
     * The larger the log2m the better the accuracy but also the higher storage
     * required.
     * 
     * @param log2m
     * @return
     */
    public AkHLL setLog2m(int log2m) {
        this.log2m = log2m;
        return this;
    }

    /**
     * Gets the number of bits per register. The larger number the better the
     * accuracy but also the higher storage required.
     * 
     * @return
     */
    public int getRegWidth() {
        return regWidth;
    }

    /**
     * Sets the number of bits per register. The larger number the better the
     * accuracy but also the higher storage required.
     * 
     * @param regWidth
     * @return
     */
    public AkHLL setRegWidth(int regWidth) {
        this.regWidth = regWidth;
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
        return hll.toBytes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL init() {
        hll = new HLL(log2m, regWidth);
        return this;
    }

    private static int reflectGetLog2m(HLL hll) {
        try {
            Field field = HLL.class.getField("log2m");
            field.setAccessible(true);
            Object value = field.get(hll);
            return value instanceof Number ? ((Number) value).intValue() : 0;
        } catch (NoSuchFieldException e) {
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int reflectGetRegWidth(HLL hll) {
        try {
            Field field = HLL.class.getField("regwidth");
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
        hll = HLL.fromBytes(data);
        this.log2m = reflectGetLog2m(hll);
        this.regWidth = reflectGetRegWidth(hll);
        return this;
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

    private final static Charset UTF8 = Charset.forName("UTF-8");

    private static long hash(HashFunction hf, Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Long || obj instanceof Integer) {
            long value = ((Number) obj).longValue();
            return hf.hashLong(value).padToLong();
        }
        if (obj instanceof Double || obj instanceof Float) {
            double value = ((Number) obj).doubleValue();
            return hf.hashLong(Double.doubleToRawLongBits(value)).padToLong();
        }
        if (obj instanceof String) {
            byte[] bytes = ((String) obj).getBytes(UTF8);
            return hf.hashBytes(bytes).padToLong();
        }
        if (obj instanceof byte[]) {
            return hf.hashBytes((byte[]) obj).padToLong();
        }
        return hash(hf, obj.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL add(Object obj) {
        if (hll == null) {
            throw new IllegalStateException();
        }
        long value = hash(hf, obj);
        hll.addRaw(value);
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
        if (!(hll instanceof AkHLL)) {
            throw new IllegalArgumentException("Argument is not of type [" + AkHLL.class + "]!");
        }
        HLL other = ((AkHLL) hll).hll;
        if (other == null) {
            throw new IllegalArgumentException("The supplied object has not been initialized!");
        }
        this.hll.union(other);
        return this;
    }

}
