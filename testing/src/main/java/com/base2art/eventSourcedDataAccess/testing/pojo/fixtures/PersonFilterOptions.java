package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import com.base2art.eventSourcedDataAccess.filtering.fields.DefaultTextField;
import lombok.Data;

@Data
public class PersonFilterOptions {

    private final TextField socialSecurityNumber = new DefaultTextField();

    private final TextField name = new DefaultTextField();
}
