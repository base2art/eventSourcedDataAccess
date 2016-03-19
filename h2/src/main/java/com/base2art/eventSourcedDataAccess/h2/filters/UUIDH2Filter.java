package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.UUIDField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.util.UUID;

public class UUIDH2Filter extends H2FilterBase<UUID, UUIDField> {

    @Override
    public void handle(final String columnName, final UUIDField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqual(columnName, filterField, collection, H2TypeRegistrar.UUID);
    }
}
