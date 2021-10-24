DELETE FROM item_category;
DELETE  FROM category_attribute;
DELETE FROM item;
DELETE FROM category;

INSERT INTO category
(id, uuid, created, updated, version, name)
VALUES (102, 'TST-CAT-UUID-1', SYSTIMESTAMP, SYSTIMESTAMP, 0, 'TST-CAT-NAME-1');

INSERT INTO category
(id, uuid, created, updated, version, name)
VALUES (101, 'TST-CAT-UUID-2', SYSTIMESTAMP, SYSTIMESTAMP, 0, 'TST-CAT-NAME-2');

INSERT INTO category_attribute
(category_id, value, idx, uuid)
VALUES (102, 'TST-CAT1-ATTR-VALUE-1', 1,'TST-CAT1-ATTR1-UUID-1' );

INSERT INTO category_attribute
(category_id, value, idx, uuid)
VALUES (102, 'TST-CAT1-ATTR-VALUE-2', 2,'TST-CAT1-ATTR1-UUID-2' );

INSERT INTO category_attribute
(category_id, value, idx, uuid)
VALUES (101, 'TST-CAT2-ATTR-VALUE-1', 1,'TST-CAT1-ATTR1-UUID-3' );

INSERT INTO category_attribute
(category_id, value, idx, uuid)
VALUES (101, 'TST-CAT2-ATTR-VALUE-2', 2,'TST-CAT1-ATTR1-UUID-4' );

INSERT INTO item
(id, uuid, created, updated, version, name, item_type)
VALUES (200, 'TST-ITM-UUID-1', SYSTIMESTAMP, SYSTIMESTAMP, 0, 'TST-ITM-NAME-1', 'SAMPLE');

INSERT INTO item
(id, uuid, created, updated, version, name, item_type)
VALUES (201, 'TST-ITM-UUID-2', SYSTIMESTAMP, SYSTIMESTAMP, 0, 'TST-ITM-NAME-2', 'DEVICE');

INSERT INTO item
(id, uuid, created, updated, version, name, item_type)
VALUES (202, 'TST-ITM-UUID-3', SYSTIMESTAMP, SYSTIMESTAMP, 0, 'TST-ITM-NAME-3', 'DEVICE');

INSERT INTO item_category
(item_id, category_id)
VALUES ( 200, 102 );

INSERT INTO item_category
(item_id, category_id)
VALUES ( 201, 102 );

INSERT INTO item_category
(item_id, category_id)
VALUES ( 201, 101 );
