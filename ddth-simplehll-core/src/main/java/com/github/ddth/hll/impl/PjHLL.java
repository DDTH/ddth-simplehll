package com.github.ddth.hll.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.github.ddth.hll.IHLL;
import com.github.ddth.hll.prasanthj.HyperLogLog;
import com.github.ddth.hll.prasanthj.HyperLogLogUtils;

/**
 * This {@link IHLL} utilizes Prasanth Jayachandran's library as the underlying
 * HyperLogLog implementation.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 * @see https://github.com/prasanthj/hyperloglog
 */
public class PjHLL implements IHLL {

    private int numRegisterIndexBits = 15, numHashBits = 128;
    private HyperLogLog hll;

    public PjHLL() {
    }

    public PjHLL(int numRegisterIndexBits, int numHashBits) {
        if (numRegisterIndexBits != 0) {
            this.numRegisterIndexBits = numRegisterIndexBits;
        }
        if (numHashBits != 0) {
            this.numHashBits = numHashBits;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PjHLL clone() {
        try {
            PjHLL clone = (PjHLL) super.clone();
            if (hll != null) {
                clone.init(this.toBytes());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the "numRegisterIndexBits" value (number of LSB hashcode bits to be
     * used as register index). The larger the numRegisterIndexBits the better
     * the accuracy but also the higher storage required.
     * 
     * @return
     */
    public int getNumRegisterIndexBits() {
        return numRegisterIndexBits;
    }

    /**
     * Set the "numRegisterIndexBits" value (number of LSB hashcode bits to be
     * used as register index). The larger the numRegisterIndexBits the better
     * the accuracy but also the higher storage required.
     * 
     * @param numRegisterIndexBits
     * @return
     */
    public PjHLL setNumRegisterIndexBits(int numRegisterIndexBits) {
        this.numRegisterIndexBits = numRegisterIndexBits;
        return this;
    }

    /**
     * Gets the number of bits for hashcode. The larger value the better the
     * accuracy but also the higher storage required.
     * 
     * @return
     */
    public int getNumHashBits() {
        return numHashBits;
    }

    /**
     * Sets the number of bits for hashcode. The larger value the better the
     * accuracy but also the higher storage required.
     * 
     * @param numHashBits
     * @return
     */
    public PjHLL setNumHashBits(int numHashBits) {
        this.numHashBits = numHashBits;
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
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            HyperLogLogUtils.serializeHLL(baos, hll);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL init() {
        HyperLogLog.HyperLogLogBuilder builder = new HyperLogLog.HyperLogLogBuilder()
                .enableBitPacking(true).enableNoBias(true)
                .setEncoding(HyperLogLog.EncodingType.DENSE).setNumHashBits(numHashBits)
                .setNumRegisterIndexBits(numRegisterIndexBits);
        hll = builder.build();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL init(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            hll = HyperLogLogUtils.deserializeHLL(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.numHashBits = hll.getNumHashBits();
        this.numRegisterIndexBits = hll.getNumRegisterIndexBits();
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
        return hll.count();
    }

    private final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * {@inheritDoc}
     */
    @Override
    public IHLL add(Object obj) {
        if (hll == null) {
            throw new IllegalStateException();
        }
        if (obj == null) {
            hll.add(0);
        } else if (obj instanceof Boolean) {
            hll.addBoolean((Boolean) obj);
        } else if (obj instanceof Byte) {
            hll.addByte((Byte) obj);
        } else if (obj instanceof Short) {
            hll.addShort((Short) obj);
        } else if (obj instanceof Integer) {
            hll.addInt((Integer) obj);
        } else if (obj instanceof Long) {
            hll.addLong((Long) obj);
        } else if (obj instanceof Double) {
            hll.addDouble((Double) obj);
        } else if (obj instanceof Float) {
            hll.addDouble((Float) obj);
        } else if (obj instanceof Character) {
            hll.addChar((Character) obj);
        } else if (obj instanceof byte[]) {
            hll.addBytes((byte[]) obj);
        } else if (obj instanceof String) {
            hll.addString((String) obj, UTF8);
        } else {
            add(obj.toString());
        }
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
        if (!(hll instanceof PjHLL)) {
            throw new IllegalArgumentException("Argument is not of type [" + PjHLL.class + "]!");
        }
        HyperLogLog other = ((PjHLL) hll).hll;
        if (other == null) {
            throw new IllegalArgumentException("The supplied object has not been initialized!");
        }
        this.hll.merge(other);
        return this;
    }

}
