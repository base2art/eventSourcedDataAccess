package com.base2art.eventSourcedDataAccess.memory;

import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoTestBase;

public class InMemoryPojoTest extends PojoTestBase {

    @Override
    protected PojoAccessorFactory get() {
        return new InMemoryPojoAccessFactory();
    }
}

