package com.base2art.eventSourcedDataAccess.filtering;

public interface BooleanField extends EquatableField<Boolean> {

    void isFalse();

    void isTrue();
}
