DELETE FROM warehouse;
DELETE FROM book_order;

INSERT INTO warehouse(id, code, count)
    VALUES
        (1, 1, 0),
        (2, 2, 2);

INSERT INTO book_order(id, item_id, state)
	VALUES
	    (1, 1, 'NEW'),
	    (2, 2, 'NEW'),
	    (3, 2, 'PROCESSING'),
	    (4, 2, 'DELIVERED'),
	    (5, 2, 'CANCELED');
