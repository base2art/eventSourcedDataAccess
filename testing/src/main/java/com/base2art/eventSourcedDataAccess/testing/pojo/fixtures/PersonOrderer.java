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
                return (x, y) -> coalesce(x.getName()).compareTo(coalesce(y.getName()));
            case NameDesc:
                return (y, x) -> coalesce(x.getName()).compareTo(coalesce(y.getName()));
            case SocialSecurityNumberAsc:
                return (x, y) -> coalesce(x.getSocialSecurityNumber()).compareTo(coalesce(y.getSocialSecurityNumber()));
            case SocialSecurityNumberDesc:
                return (y, x) -> coalesce(x.getSocialSecurityNumber()).compareTo(coalesce(y.getSocialSecurityNumber()));
        }

        return null;
    }

    private String coalesce(final String name) {
        return name == null ? "" : name;
    }
}
