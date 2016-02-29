package com.base2art.eventSourcedDataAccess.extensions;

import java.util.stream.Stream;

public interface Filterer<ObjectEntity, FilterOptions> {

    Stream<ObjectEntity> filter(final Stream<ObjectEntity> stream, final FilterOptions orderOptions);
}
