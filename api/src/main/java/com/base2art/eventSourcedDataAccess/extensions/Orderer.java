package com.base2art.eventSourcedDataAccess.extensions;

import java.util.stream.Stream;

public interface Orderer<ObjectEntity, OrderOptions> {

    Stream<ObjectEntity> order(final Stream<ObjectEntity> stream, final OrderOptions orderOptions);
}
