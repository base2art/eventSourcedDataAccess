package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultTextField;
import lombok.Data;

@Data
public class PersonFilterOptions {

    private final TextField socialSecurityNumber = new DefaultTextField();
}
