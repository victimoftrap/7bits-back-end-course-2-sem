CREATE TABLE tasks (
  id CHAR(36) NOT NULL PRIMARY KEY,
  text VARCHAR NOT NULL,
  status VARCHAR NOT NULL DEFAULT 'inbox',
  createdAt VARCHAR NOT NULL
);