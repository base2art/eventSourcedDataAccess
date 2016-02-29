package extensions.filterTypes;

import extensions.filterTypes.conditions.NumberCondition;

public interface NumberField {

    String name();

    NumberCondition between(int lower, int upper);

    NumberCondition equalTo(int value);

    NumberCondition greaterThan(int value);

    NumberCondition greaterThanOrEqualTo(int value);

    NumberCondition lessThan(String value);

    NumberCondition lessThanOrEqualTo(String value);


//    Integer equalTo();
//
//    Integer lessThan();
//
//    Integer greaterThan();
}
