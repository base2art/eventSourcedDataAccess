package extensions.filterTypes;

import extensions.filterTypes.conditions.TextCondition;

import java.util.regex.Pattern;

public interface TextField {

//    String name();

    // ignoreCase?
    TextCondition contains(String value);

    TextCondition matches(Pattern value);

    TextCondition between(String lower, String upper);

    TextCondition endsWith(String ending);

    TextCondition equalTo(String value);

    TextCondition notEqualTo(String value);

    TextCondition equalToIgnoreCase(String value);

    TextCondition greaterThan(String value);

    TextCondition greaterThanOrEqualTo(String value);

    TextCondition lessThan(String value);

    TextCondition lessThanOrEqualTo(String value);

    TextCondition in(String... values);

    TextCondition isNotNullOrEmpty();

}
