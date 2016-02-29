package com.base2art.eventSourcedDataAccess.extensions;

import java.util.Comparator;
import java.util.function.Function;

public class DefaultOrderer<ObjectEntity, OrderOptions> extends OrdererBase<ObjectEntity, OrderOptions> {

    private final Function<OrderOptions, Comparator<ObjectEntity>> orderFunctionProvider;

    public DefaultOrderer(Function<OrderOptions, Comparator<ObjectEntity>> orderFunctionProvider) {
        this.orderFunctionProvider = orderFunctionProvider;
    }


    @Override
    protected Comparator<ObjectEntity> getComparator(final OrderOptions orderOptions) {
        if (orderFunctionProvider == null) {
            return null;
        }

        return this.orderFunctionProvider.apply(orderOptions);
    }
}