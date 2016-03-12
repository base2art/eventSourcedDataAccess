
CREATE TABLE IF NOT EXISTS {objectTableName} (
  object_id {objectIdTypeName} NOT NULL,
  time_stamp TIMESTAMP DEFAULT NOW()
);


CREATE TABLE IF NOT EXISTS {objectStatusTableName} (

  object_status_version_id IDENTITY NOT NULL,
  object_id {objectIdTypeName} NOT NULL,
  isArchived BOOLEAN NOT NULL,
  time_stamp TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS {objectVersionTableName} (
  object_version_id IDENTITY NOT NULL,
  object_id {objectIdTypeName} NOT NULL,
  time_stamp TIMESTAMP DEFAULT NOW()
)

