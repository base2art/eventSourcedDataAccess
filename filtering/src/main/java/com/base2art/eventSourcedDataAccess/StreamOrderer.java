package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface StreamOrderer<ObjectEntity, OrderOptions> {

    Stream<ObjectEntity> order(final Stream<ObjectEntity> stream, final OrderOptions orderOptions);
}
