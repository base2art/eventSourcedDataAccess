
SELECT * FROM {objectTableName} oq
    WHERE object_id in (

                        SELECT p1.object_id
                              FROM {objectVersionTableName} p1
                              LEFT JOIN {objectVersionTableName} p2
                                    ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)
                              WHERE p2.object_version_id IS NULL {objectVersionClause}
                       )
    {objectClause};
