package com.base2art.eventSourcedDataAccess.filtering;

import java.util.Optional;

public interface TextFilterField extends ComparableFilterField<String> {

    // ignoreCase?
    void contains(String value);

    Optional<String> contains();

//    void matches(Pattern value);
//
//    Optional<Pattern> matches();

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
