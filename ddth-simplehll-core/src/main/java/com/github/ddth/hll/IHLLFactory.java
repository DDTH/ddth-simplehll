package com.github.ddth.hll;

/**
 * Factory to create {@link IHLL} objects.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.2
 */
public interface IHLLFactory {
    /**
     * Creates a new {@link IHLL} object.
     * 
     * @return
     */
    public IHLL createHLL();
}
