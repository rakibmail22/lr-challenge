CREATE TABLE IF NOT EXISTS category
(
    id      NUMBER(19, 0)      NOT NULL PRIMARY KEY,
    uuid    VARCHAR2(64 CHAR)  NOT NULL UNIQUE,
    created TIMESTAMP          NOT NULL,
    updated TIMESTAMP,
    version NUMBER(10, 0)      NOT NULL,
    name    VARCHAR2(128 CHAR) NOT NULL
);

CREATE TABLE IF NOT EXISTS category_attribute
(
    category_id NUMBER(19, 0)      NOT NULL,
    value       VARCHAR2(128 CHAR) NOT NULL,
    idx         NUMBER(19, 0)      NOT NULL,
    uuid        VARCHAR2(64 CHAR)  NOT NULL UNIQUE,
    constraint fk_cat_att_cat_id FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id        NUMBER(19, 0)      NOT NULL PRIMARY KEY,
    uuid      VARCHAR2(64 CHAR)  NOT NULL UNIQUE,
    created   TIMESTAMP          NOT NULL,
    updated   TIMESTAMP,
    version   NUMBER(10, 0)      NOT NULL,
    name      VARCHAR2(128 CHAR) NOT NULL,
    item_type VARCHAR2(16 CHAR)
);

CREATE TABLE IF NOT EXISTS item_category
(
    item_id     NUMBER(19, 0) NOT NULL,
    category_id NUMBER(19, 0) NOT NULL,
    CONSTRAINT pk_itm_cat_id PRIMARY KEY (item_id, category_id),
    CONSTRAINT fk_itm_cat_itm_id FOREIGN KEY (item_id) REFERENCES item (id),
    CONSTRAINT fk_itm_cat_cat_id FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE SEQUENCE IF NOT EXISTS category_sequence START WITH 1 INCREMENT BY 2;
CREATE SEQUENCE IF NOT EXISTS item_sequence START WITH 1 INCREMENT BY 2;