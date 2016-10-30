package com.github.ddth.hll;

import java.util.Arrays;

/**
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.1
 */
public class HLLUtils {

    private final static byte[] MAGIC_BYTES = { 19, 81 };

    /**
     * Serialzies a {@link IHLL}.
     * 
     * <p>
     * Format:
     * </p>
     * <ul>
     * <li>First 2 bytes: magic number (0x1981)</li>
     * <li>Next 1 byte: HLL implementation ID. {@link PjHLL}: 1, {@link AkHLL}:
     * 2, {@link AtsHLL}: 3</li>
     * <li>Next n bytes: result from {@link IHLL#toBytes()}</li>
     * </ul>
     * 
     * @param hll
     * @return
     */
    public static byte[] toBytes(IHLL hll) {
        if (hll == null) {
            return null;
        }
        byte[] hllBytes = hll.toBytes();
        byte[] result = new byte[hllBytes.length + 3];
        result[0] = MAGIC_BYTES[0];
        result[1] = MAGIC_BYTES[1];
        if (hll instanceof PjHLL) {
            result[2] = 1;
        } else if (hll instanceof AkHLL) {
            result[2] = 2;
        } else if (hll instanceof AtsHLL) {
            result[2] = 3;
        } else {
            throw new IllegalArgumentException("Unsupported class [" + hll.getClass() + "]!");
        }
        for (int i = 0; i < hllBytes.length; i++) {
            result[i + 3] = hllBytes[i];
        }
        return result;
    }

    /**
     * Deserializes a {@link IHLL}.
     * 
     * @param data
     * @return
     */
    public static IHLL fromBytes(byte[] data) {
        if (data == null) {
            return null;
        }
        if (data.length < 4) {
            throw new IllegalArgumentException();
        }
        if (data[0] != MAGIC_BYTES[0] || data[1] != MAGIC_BYTES[1]) {
            throw new IllegalArgumentException(
                    "Invalid data signature [" + data[0] + "," + data[1] + "]!");
        }
        switch (data[2]) {
        case 1:
            return new PjHLL().init(Arrays.copyOfRange(data, 3, data.length));
        case 2:
            return new AkHLL().init(Arrays.copyOfRange(data, 3, data.length));
        case 3:
            return new AtsHLL().init(Arrays.copyOfRange(data, 3, data.length));
        default:
            throw new IllegalArgumentException("Unsupported HLL implementation [" + data[2] + "]!");
        }
    }

}
