package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoTestBase;
import org.junit.Ignore;

public class H2PojoTest extends PojoTestBase {

    @Override
    protected PojoAccessorFactory get() {
        return new H2PojoAccessFactory();
    }

//    @Override
//    @Ignore("Suppressing while testing from command line")
//    public void canReadManyItemsFilteredAndPaged() throws DataAccessReaderException, DataAccessWriterException {
////        super.canReadManyItemsFilteredAndPaged();
//    }
}
