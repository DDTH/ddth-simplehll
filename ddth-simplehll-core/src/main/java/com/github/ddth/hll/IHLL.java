package com.github.ddth.hll;

/**
 * High level HyperLogLog API.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public interface IHLL {

    /**
     * Serializes this HLL to byte array.
     * 
     * @return
     */
    public byte[] toBytes();

    /**
     * Initializes this HLL.
     * 
     * @return
     */
    public IHLL init();

    /**
     * Initializes this HLL from a byte array (output from from
     * {@link #toBytes()}).
     * 
     * @param data
     * @return
     */
    public IHLL init(byte[] data);

    /**
     * Returns the (estimated) number of items have been added.
     * 
     * @return
     */
    public long count();

    /**
     * Adds an item.
     * 
     * @param obj
     * @return
     */
    public IHLL add(Object obj);

    /**
     * Merges another HLL to this one.
     * 
     * @param hll
     * @return
     */
    public IHLL merge(IHLL hll);

}
