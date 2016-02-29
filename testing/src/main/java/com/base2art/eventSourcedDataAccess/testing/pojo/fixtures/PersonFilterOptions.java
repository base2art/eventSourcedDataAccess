package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.testing.pojo.EndsWithFilter;

public class PersonFilterOptions {
    private final EndsWithFilter endsWithFilter;

    public PersonFilterOptions() {
        this.endsWithFilter = null;
    }

    public PersonFilterOptions(final EndsWithFilter endsWithFilter) {

        this.endsWithFilter = endsWithFilter;
    }

    public EndsWithFilter getEndsWithFilter() {
        return endsWithFilter;
    }
}
