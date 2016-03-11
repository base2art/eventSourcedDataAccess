package com.base2art.eventSourcedDataAccess.filtering.fields;

import com.base2art.eventSourcedDataAccess.filtering.BooleanField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true, chain = false)
public class DefaultBooleanField implements BooleanField {

    @Setter
    private Boolean equalTo;

    @Override
    public void isFalse() {
        this.equalTo(false);
    }

    @Override
    public void isTrue() {
        this.equalTo(true);
    }

    @Override
    public void in(final Boolean[] values) {
        if (values == null || values.length == 0) {
            this.equalTo(null);
        }
        else {
            this.equalTo(values[0]);
        }
    }

    @Override
    public Optional<Boolean[]> in() {
        return this.equalTo().map(x -> new Boolean[]{ x });
    }

    @Override
    public Optional<Boolean> equalTo() {
        return Optional.ofNullable(this.equalTo);
    }

    @Override
    public void notEqualTo(final Boolean value) {
        if (value != null) {
            this.equalTo(!value);
        }
        else {
            this.equalTo(null);
        }
    }

    @Override
    public Optional<Boolean> notEqualTo() {
        return this.equalTo().map(x -> !x);
    }
}
