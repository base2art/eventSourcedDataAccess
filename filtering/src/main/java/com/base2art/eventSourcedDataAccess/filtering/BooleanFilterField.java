package com.base2art.eventSourcedDataAccess.filtering;

public interface BooleanFilterField extends EquatableFilterField<Boolean> {

    void isFalse();

    void isTrue();
}
