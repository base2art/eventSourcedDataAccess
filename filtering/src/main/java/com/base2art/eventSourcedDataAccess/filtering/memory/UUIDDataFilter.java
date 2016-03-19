package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.UUIDFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.util.UUID;

public class UUIDDataFilter extends DataFilterBase<UUID, UUIDFilterField> {

    @Override
    public boolean handle(final UUID type, final UUIDFilterField filterField) {

        return CompareUtils.handleEqual(type, filterField);
    }
}
