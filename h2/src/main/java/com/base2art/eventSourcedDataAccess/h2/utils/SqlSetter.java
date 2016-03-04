package com.base2art.eventSourcedDataAccess.h2.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlSetter {
    void accept(PreparedStatement statement, int position, Object object) throws SQLException;
}