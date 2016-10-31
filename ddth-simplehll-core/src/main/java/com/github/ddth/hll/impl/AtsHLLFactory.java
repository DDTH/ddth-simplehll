package com.github.ddth.hll.impl;

import com.github.ddth.hll.IHLLFactory;

/**
 * Factory to create {@link AkHLL} objects.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.2
 */
public class AtsHLLFactory implements IHLLFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public AtsHLL createHLL() {
        return (AtsHLL) new AtsHLL().init();
    }

}
