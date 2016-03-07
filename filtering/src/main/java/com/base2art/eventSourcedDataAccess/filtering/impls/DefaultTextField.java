package com.base2art.eventSourcedDataAccess.filtering.impls;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.regex.Pattern;

@Accessors(fluent = true, chain = false)
public class DefaultTextField extends DefaultComparableField<String> implements TextField {

    @Setter
    private String contains;

    @Setter
    private Pattern matches;

    @Setter
    private String endsWith;

    @Setter
    private String equalToIgnoreCase;

    @Setter
    private Boolean isNotNullOrEmpty;

    public Optional<String> contains() {
        return Optional.ofNullable(this.contains).map(this::clean);
    }

    public Optional<Pattern> matches() {
        return Optional.ofNullable(this.matches);
    }

    public Optional<String> endsWith() {
        return Optional.ofNullable(this.endsWith).map(this::clean);
    }

    public Optional<String> equalToIgnoreCase() {
        return Optional.ofNullable(this.equalToIgnoreCase).map(this::clean);
    }

    public Optional<Boolean> isNotNullOrEmpty() {
        return Optional.ofNullable(this.isNotNullOrEmpty).map(x -> x ? true : null);
    }

    @Override
    protected String clean(final String item) {
        return item.isEmpty() ? null : item;
    }
}