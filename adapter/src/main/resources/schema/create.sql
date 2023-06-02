CREATE TABLE IF NOT EXISTS orders
(
    id                	VARCHAR(36)  	PRIMARY KEY,
    orderer_user_id     VARCHAR(36) 	NOT NULL,
    orderer_name		VARCHAR(40)		NOT NULL,
    orderer_phone		VARCHAR(15)		NOT NULL,
    status				VARCHAR(20)		NOT NULL,
    total_amount        INT             NOT NULL,
    receiver_name		VARCHAR(40)		NOT NULL,
    receiver_phone		VARCHAR(15)		NOT NULL,
    shipping_zipcode	VARCHAR(10)		NOT NULL,
    shipping_first_line	    VARCHAR(100)		NOT NULL,
    shipping_second_line	VARCHAR(100)		NOT NULL,
    shipping_message    VARCHAR(1000),
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    modified_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    version             BIGINT
);

CREATE INDEX IF NOT EXISTS orders_idx_1 ON orders(id);

CREATE TABLE IF NOT EXISTS order_lines
(
    order_id         	VARCHAR(36) 	NOT NULL,
    idx                 INT             NOT NULL,
    product_option_id	VARCHAR(36)		NOT NULL,
    price				INT				NOT NULL,
    quantity			INT				NOT NULL,
	version 			BIGINT,
	PRIMARY KEY (order_id, idx)
);

CREATE INDEX IF NOT EXISTS order_lines_idx_1 ON order_lines(order_id, idx);

CREATE TABLE IF NOT EXISTS order_payment_infos
(
    order_id         	VARCHAR(36) 	NOT NULL,
    idx                 INT             NOT NULL,
    method				VARCHAR(20)		NOT NULL,
    amount				INT				NOT NULL,
	version 			BIGINT,
	PRIMARY KEY (order_id, idx)
);

CREATE INDEX IF NOT EXISTS order_payment_infos_idx_1 ON order_payment_infos(order_id, idx);
