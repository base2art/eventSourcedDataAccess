//package com.base2art.eventSourcedDataAccess.utils.extensions;
//
//import java.util.Comparator;
//import java.util.stream.Stream;
//
//public abstract class OrdererBase<ObjectEntity, OrderOptions> implements Orderer<ObjectEntity, OrderOptions> {
//
//
//    @Override
//    public Stream<ObjectEntity> order(final Stream<ObjectEntity> stream, final OrderOptions orderOptions) {
//        if (orderOptions == null) {
//            return stream;
//        }
//
//        Comparator<ObjectEntity> comp = this.getComparator(orderOptions);
//
//        if (comp == null) {
//            return stream;
//        }
//
//        return stream.sorted(comp);
//    }
//
//    protected abstract Comparator<ObjectEntity> getComparator(final OrderOptions orderOptions);
//}
