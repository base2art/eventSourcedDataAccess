package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.extensions.DefaultFilterer;

public class PersonFilterer extends DefaultFilterer<Person, PersonFilterOptions> {
    public PersonFilterer() {
        super(PersonFilterer::filterItem);
    }

    private static boolean filterItem(final Person person, final PersonFilterOptions personFilterOptions) {

        if (personFilterOptions.getEndsWithFilter() == null || personFilterOptions.getEndsWithFilter().getSuffix() == null ||
            personFilterOptions.getEndsWithFilter().getSuffix().isEmpty()) {
            return true;
        }

        return person.getName().endsWith(personFilterOptions.getEndsWithFilter().getSuffix());
    }
}
