CREATE TABLE users
(
    id       CHAR(36) PRIMARY KEY,
    login    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    status   VARCHAR(255)        NOT NULL DEFAULT 'active'
);