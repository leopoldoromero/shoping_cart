CREATE TABLE cart (
    id VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE cart_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    quantity INTEGER NOT NULL,
    price INTEGER NOT NULL,
    stock INTEGER NOT NULL,
    code VARCHAR(255),
    image JSON,
    cart_id VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES cart (id)
);

CREATE TABLE cart_discount (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
amount INTEGER NOT NULL,
type VARCHAR(255),
individualUse BOOLEAN,
code VARCHAR(255),
cart_id VARCHAR(255),
FOREIGN KEY (cart_id) REFERENCES cart (id)
);
