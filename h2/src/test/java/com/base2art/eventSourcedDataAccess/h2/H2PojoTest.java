package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoTestBase;

public class H2PojoTest extends PojoTestBase {

    @Override
    protected PojoAccessorFactory get() {
        return new H2PojoAccessFactory();
    }
}
