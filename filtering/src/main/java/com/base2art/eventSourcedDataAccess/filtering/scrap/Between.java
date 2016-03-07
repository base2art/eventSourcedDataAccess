//package com.base2art.eventSourcedDataAccess.filtering;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class Between<T extends Comparable<T>> {
//
//    private final T lower;
//
//    private final T upper;
//
//    public static <A extends Comparable<A>> Between<A> of(A lower, A upper) {
//
//        Between<A> between = new Between<>(lower, upper);
//        return between;
//    }
//}
//
