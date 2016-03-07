package com.base2art.eventSourcedDataAccess.filtering;

import java.util.Optional;
import java.util.regex.Pattern;

public interface TextField extends ComparableField<String> {

    // ignoreCase?
    void contains(String value);

    Optional<String> contains();

    void matches(Pattern value);

    Optional<Pattern> matches();

    void endsWith(String ending);

    Optional<String> endsWith();

    void equalToIgnoreCase(String value);

    Optional<String> equalToIgnoreCase();

    void isNotNullOrEmpty(Boolean value);

    Optional<Boolean> isNotNullOrEmpty();

    default void requireIsNotNullOrEmpty() {
        this.isNotNullOrEmpty(true);
    }
}
