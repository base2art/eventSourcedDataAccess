package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.utils.ResourceReader;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass
public class Sql {

    public static <Id> String paged(
            final H2Connector<Id> connector,
            final Id marker,
            final String sortField,
            final boolean isAscending,
            final H2ClauseCollection objectJoiner,
            final H2ClauseCollection versionJoiner) {

        String sql =
                marker == null
                ? ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/paged-first.sql", loader())
                : ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/paged.sql", loader());

        boolean isSortOnObject = !connector.nonFinalObjectVersionDataFields()
                                           .stream()
                                           .filter(x -> sortField.equalsIgnoreCase(x.getName()))
                                           .findAny()
                                           .isPresent();

        final String objectClause = objectJoiner == null || objectJoiner.length() == 0 ? "" : " AND " + objectJoiner.join(" AND ");
        final String versionClause = versionJoiner == null || versionJoiner.length() == 0 ? "" : " AND " + versionJoiner.join(" AND ");

        return replaceCommon(sql, connector)
                .replace("{sortColumnTable[ot|p1]}", isSortOnObject ? "ot" : "p1")
                .replace("{sortColumnTable[pj|po1]}", isSortOnObject ? "pj" : "po1")
                .replace("{sortColumn}", sortField)
                .replace("{objectClause}", objectClause)
                .replace("{objectVersionClause}", versionClause)
                .replace("{sortDirection}", isAscending ? "ASC" : "DESC");
    }

    public static <Id> String filtered(
            final H2Connector<Id> connector,
            final H2ClauseCollection objectJoiner,
            final H2ClauseCollection versionJoiner) {

        return paged(connector, null, "time_stamp", false, objectJoiner, versionJoiner);
    }

    public static <Id> String latestVersionByObjectId(final H2Connector<Id> connector) {

        val sql = ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/latest-version-by-object-id.sql", loader());

        return replaceCommon(sql, connector);
    }

    public static <Id> String ddl(final H2Connector<Id> connector, final int ddlVersion) {

        val sql = ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/ddl-" + ddlVersion + ".sql", loader());

        return replaceCommon(sql, connector);
    }

    private static <Id> String replaceCommon(final String sql, final H2Connector<Id> connector) {
        return sql.replace("{objectTableName}", connector.objectTable())
                  .replace("{objectVersionTableName}", connector.objectVersionTable())
                  .replace("{objectStatusTableName}", connector.objectStatusTable())
                  .replace("{objectIdTypeName}", connector.idH2Type().getTypeName());
    }

    private static ClassLoader loader() {
        return Sql.class.getClassLoader();
    }
}
