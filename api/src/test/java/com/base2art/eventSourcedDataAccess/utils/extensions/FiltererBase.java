//package com.base2art.eventSourcedDataAccess.utils.extensions;
//
//import java.util.stream.Stream;
//
//public abstract class FiltererBase<ObjectEntity, FilterOptions> implements Filterer<ObjectEntity, FilterOptions> {
//
//
//    @Override
//    public Stream<ObjectEntity> filter(final Stream<ObjectEntity> stream, final FilterOptions filterOptions) {
//        if (filterOptions == null) {
//            return stream;
//        }
//
//        return stream.filter((x) -> this.test(x, filterOptions));
//    }
//
//    protected abstract boolean test(final ObjectEntity x, final FilterOptions filterOptions);
//}
