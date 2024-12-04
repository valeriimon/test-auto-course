DELETE from book_order;
INSERT INTO book_order(id, item_id, state)
	VALUES
	    (1, 1, 'NEW'),
	    (2, 2, 'PROCESSING'),
	    (3, 2, 'DELIVERED'),
	    (4, 2, 'CANCELED');
