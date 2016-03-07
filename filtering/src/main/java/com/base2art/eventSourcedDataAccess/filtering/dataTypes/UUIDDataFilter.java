package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.UUIDField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultUUIDField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.util.UUID;

public class UUIDDataFilter extends DataFilterBase<UUID, UUIDField, DefaultUUIDField> {
//    public UUIDDataFilter() {
//        super(null, UUID.class, UUIDField.class, DefaultUUIDField.class);
//    }

    @Override
    protected boolean handle(final UUID type, final UUIDField filterField) {

        return CompareUtils.handleEqual(type, filterField);
    }
}
