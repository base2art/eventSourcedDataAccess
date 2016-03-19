package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.UUIDField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.util.UUID;

public class UUIDDataFilter extends DataFilterBase<UUID, UUIDField> {

    @Override
    public boolean handle(final UUID type, final UUIDField filterField) {

        return CompareUtils.handleEqual(type, filterField);
    }
}
