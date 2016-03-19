package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.parameters.H2Type;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

public class StringH2Filter extends H2FilterBase<String, TextField> {

    @Override
    public void handle(final String columnName, final TextField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.String);

        // DO ThE REST;

        H2Type<String> setup = H2TypeRegistrar.String;

        if (filterField.contains().isPresent()) {
            collection.add(1, columnName + " LIKE ?", (s, pos) -> setup.setValue(s, pos, "%" + filterField.contains().get() + "%"));
        }

//        if (filterField.matches().isPresent()) {
//            collection.add(columnName + " LIKE '%' + ? + '%'", (s, pos) -> setup.setValue(s, pos, filterField.matches().get().pattern()));
//        }

        if (filterField.endsWith().isPresent()) {
            collection.add(1, columnName + " LIKE ?", (s, pos) -> setup.setValue(s, pos, "%" + filterField.endsWith().get()));
        }

        if (filterField.equalToIgnoreCase().isPresent()) {
            collection.add(1, "UPPER(" + columnName + ") = UPPER(?)", (s, pos) -> setup.setValue(s, pos, filterField.endsWith().get()));
        }

        if (filterField.isNotNullOrEmpty().isPresent()) {
            if (filterField.isNotNullOrEmpty().get()) {
                collection.add(0, "(" + columnName + " is null or " + columnName + " = '')", null);
            }
            else {
                collection.add(0, "DATALENGTH(" + columnName + ") > 0", null);
            }
        }
    }
}
