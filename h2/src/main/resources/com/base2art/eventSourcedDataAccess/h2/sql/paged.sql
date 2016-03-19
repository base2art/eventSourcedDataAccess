
SELECT pj.*
  FROM (
        SELECT porn.Object_Id
          FROM (
                SELECT rownum() as num, poo.Object_id
                    FROM (
                          SELECT ot.Object_id
                            FROM {objectTableName} ot
                            LEFT JOIN {objectVersionTableName} p1
                              ON (p1.object_id = ot.object_id)
                            LEFT JOIN {objectVersionTableName} p2
                              ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)
                            WHERE p2.object_version_id IS NULL {objectVersionClause} {objectClause}
                            ORDER BY {sortColumnTable[ot|p1]}.{sortColumn} {sortDirection}
                      ) poo
               ) porn
          WHERE porn.num >  (
                              SELECT num, tat.object_id
                                  FROM (
                                        SELECT rownum() as num, poo.Object_id
                                            FROM (
                                                  SELECT ot.Object_id
                                                      FROM {objectTableName} ot
                                                      LEFT JOIN {objectVersionTableName} p1
                                                        ON (p1.object_id = ot.object_id)
                                                      LEFT JOIN {objectVersionTableName} p2
                                                        ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)

                                                      WHERE p2.object_version_id IS NULL {objectVersionClause} {objectClause}
                                                      ORDER BY {sortColumnTable[ot|p1]}.{sortColumn} {sortDirection}
                                                  ) poo
                                        ) tat
                                  WHERE tat.Object_Id = ?
                            )
          LIMIT ?
       ) pooq


  LEFT JOIN {objectTableName} pj
    ON pj.Object_id=pooq.Object_Id

  LEFT JOIN {objectVersionTableName} po1
    ON (po1.object_id = pj.object_id)
  LEFT JOIN {objectVersionTableName} po2
    ON (po1.object_id = po2.object_id) AND (po1.OBJECT_VERSION_ID < po2.OBJECT_VERSION_ID)

  ORDER BY {sortColumnTable[pj|po1]}.{sortColumn} {sortDirection}
