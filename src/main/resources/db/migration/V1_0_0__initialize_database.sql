CREATE TABLE customer
(
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255),
    surname     VARCHAR(255)
);

CREATE TABLE account
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    label       VARCHAR(255),
    balance     DECIMAL(19, 2)
);

ALTER TABLE account
    ADD CONSTRAINT fk_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id);

CREATE TABLE transaction
(
    id         BIGINT AUTO_INCREMENT  PRIMARY KEY,
    account_id BIGINT,
    amount     DECIMAL(19, 2),
    label      VARCHAR(255)
);

ALTER TABLE transaction
    ADD CONSTRAINT fk_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id);
