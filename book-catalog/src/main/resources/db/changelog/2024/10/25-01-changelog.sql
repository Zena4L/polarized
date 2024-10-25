-- liquibase formatted sql

-- changeset ClementOwireku-Bogya:1729850308112-1
CREATE SEQUENCE IF NOT EXISTS book_seq START WITH 1 INCREMENT BY 50;

-- changeset ClementOwireku-Bogya:1729850308112-2
CREATE TABLE book
(
    id                 BIGINT       NOT NULL,
    isbn               VARCHAR(255) NOT NULL,
    author             VARCHAR(255) NOT NULL,
    title              VARCHAR(255) NOT NULL,
    price              DECIMAL      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    version            INTEGER,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

