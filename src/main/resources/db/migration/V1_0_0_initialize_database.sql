CREATE TABLE customer
(
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    surname     VARCHAR(255)
);

CREATE TABLE account
(
    id          SERIAL PRIMARY KEY,
    customer_id BIGINT,
    label       VARCHAR(255),
    balance     NUMERIC(19, 2)
);

ALTER TABLE account
    ADD CONSTRAINT fk_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id);

CREATE TABLE transaction
(
    id         SERIAL PRIMARY KEY,
    account_id BIGINT,
    amount     NUMERIC(19, 2),
    label      VARCHAR(255)
);

ALTER TABLE transaction
    ADD CONSTRAINT fk_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id);
