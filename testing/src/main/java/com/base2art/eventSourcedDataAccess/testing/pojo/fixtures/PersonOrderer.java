package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.extensions.OrdererBase;

import java.util.Comparator;

public class PersonOrderer extends OrdererBase<Person, PersonOrderOptions> {

    @Override
    protected Comparator<Person> getComparator(final PersonOrderOptions personOrderOptions) {

        switch (personOrderOptions) {
            case IdAsc:
                return (x, y) -> x.getId().compareTo(y.getId());
            case IdDesc:
                return (y, x) -> x.getId().compareTo(y.getId());
            case NameAsc:
                return (x, y) -> x.getName().compareTo(y.getName());
            case NameDesc:
                return (y, x) -> x.getName().compareTo(y.getName());
            case SocialSecurityNumberAsc:
                return (x, y) -> x.getSocialSecurityNumber().compareTo(y.getSocialSecurityNumber());
            case SocialSecurityNumberDesc:
                return (y, x) -> x.getSocialSecurityNumber().compareTo(y.getSocialSecurityNumber());
        }

        return null;
    }
}
