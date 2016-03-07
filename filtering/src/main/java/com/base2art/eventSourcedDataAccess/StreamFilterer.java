package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface StreamFilterer<ObjectEntity, FilterOptions> {

    Stream<ObjectEntity> filter(final Stream<ObjectEntity> stream, final FilterOptions filterOptions);
}
