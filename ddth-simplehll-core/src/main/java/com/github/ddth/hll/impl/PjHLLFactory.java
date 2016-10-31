package com.github.ddth.hll.impl;

import com.github.ddth.hll.IHLLFactory;

/**
 * Factory to create {@link PjHLL} objects.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.2
 */
public class PjHLLFactory implements IHLLFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public PjHLL createHLL() {
        return (PjHLL) new PjHLL().init();
    }

}
