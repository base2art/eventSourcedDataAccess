package com.base2art.eventSourcedDataAccess.filtering.fields;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true, chain = false)
public class DefaultEquatableField<T extends Comparable<T>> implements SimpleEquatableField<T> {

    @Setter
    private T equalTo;

    @Setter
    private T notEqualTo;

    @Setter
    private T[] in;

    public Optional<T> equalTo() {
        return Optional.ofNullable(this.equalTo);
    }

    public Optional<T> notEqualTo() {
        return Optional.ofNullable(this.notEqualTo);
    }

    public Optional<T[]> in() {

        T[] values = this.in;
        if (values == null || values.length == 0) {
            return Optional.empty();
        }

        return Optional.of(this.in);
    }

    protected T clean(T item) {
        return item;
    }
}
