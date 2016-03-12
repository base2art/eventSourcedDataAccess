
SELECT pj.*
  FROM (
        SELECT porn.Object_Id
          FROM (
                SELECT rownum() as num, poo.Object_id
                    FROM (
                           SELECT Object_Id
                               FROM {objectTableName}
                               ORDER BY {sortColumn} {sortDirection}
                          ) poo
               ) porn
          WHERE porn.num >  0
          LIMIT ?
       ) pooq


  LEFT JOIN {objectTableName} pj
    ON pj.Object_id=pooq.Object_Id
  ORDER BY {sortColumn} {sortDirection}
