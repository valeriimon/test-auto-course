DELETE FROM warehouse;
DELETE FROM book_order;

INSERT INTO warehouse(id, code, count)
    VALUES
        (1, 1, 0),
        (2, 2, 2);