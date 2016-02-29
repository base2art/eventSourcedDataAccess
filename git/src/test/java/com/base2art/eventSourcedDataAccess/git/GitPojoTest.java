package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoTestBase;

public class GitPojoTest extends PojoTestBase {

    @Override
    protected PojoAccessorFactory get() {
        final GitPojoAccessorFactory gitPojoAccessorFactory = new GitPojoAccessorFactory();
        gitPojoAccessorFactory.ensureUpdated();
        return gitPojoAccessorFactory;
    }
}
