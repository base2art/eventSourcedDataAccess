//package com.base2art.eventSourcedDataAccess.utils.extensions;
//
//import java.util.function.BiPredicate;
//
//public class DefaultFilterer<ObjectEntity, FilterOptions> extends FiltererBase<ObjectEntity, FilterOptions> {
//
//    private final BiPredicate<ObjectEntity, FilterOptions> filter;
//
//    public DefaultFilterer() {
//        this(null);
//    }
//
//    public DefaultFilterer(BiPredicate<ObjectEntity, FilterOptions> filter) {
//        this.filter = filter == null
//                      ? (x, y) -> true
//                      : filter;
//    }
//
//    @Override
//    protected boolean test(final ObjectEntity x, final FilterOptions filterOptions) {
//        return this.filter.test(x, filterOptions);
//    }
//}
