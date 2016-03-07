//package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;
//
//import com.base2art.eventSourcedDataAccess.DefaultFilterer;
//
//public class PersonFilterer extends DefaultFilterer<Person, PersonFilterOptions> {
//    public PersonFilterer() {
//        super(PersonFilterer::filterItem);
//    }
//
//    private static boolean filterItem(final Person person, final PersonFilterOptions personFilterOptions) {
//
//        if (personFilterOptions.getSocialSecurityNumber().endsWith() == null ||
//            personFilterOptions.getSocialSecurityNumber().endsWith().isPresent()) {
//            return true;
//        }
//
//        return person.getName().endsWith(personFilterOptions.getSocialSecurityNumber().endsWith().get());
//    }
//}
