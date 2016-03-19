package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.filtering.TextFilterField;
import com.base2art.eventSourcedDataAccess.filtering.fields.DefaultTextFilterField;
import lombok.Data;

@Data
public class PersonFilterOptions {

    private final TextFilterField socialSecurityNumber = new DefaultTextFilterField();

    private final TextFilterField name = new DefaultTextFilterField();
}
