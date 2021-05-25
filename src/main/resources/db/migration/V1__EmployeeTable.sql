CREATE TABLE employee
(
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    title       VARCHAR(100) NOT NULL,
    anniversary INT          NOT NULL
);