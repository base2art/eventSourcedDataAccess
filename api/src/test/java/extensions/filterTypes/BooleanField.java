package extensions.filterTypes;

import extensions.filterTypes.conditions.BooleanCondition;

public interface BooleanField {


    BooleanCondition equalTo(String value);

    BooleanCondition isFalse();

    BooleanCondition isTrue();
}
