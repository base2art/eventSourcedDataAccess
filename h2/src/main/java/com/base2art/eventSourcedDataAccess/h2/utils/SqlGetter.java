package com.base2art.eventSourcedDataAccess.h2.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlGetter<V> {
    Object accept(ResultSet resultSet, String index) throws SQLException;
}