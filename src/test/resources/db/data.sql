DELETE FROM transaction;
DELETE FROM account;
DELETE FROM customer;

INSERT INTO CUSTOMER (customer_id, name, surname) VALUES (1, 'Oussama', 'Messaoudi');

commit;