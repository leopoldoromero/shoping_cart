CREATE TABLE cart (
    id VARCHAR(45) NOT NULL,
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP,
    customerId VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart_item (
    id VARCHAR(45) NOT NULL,
    quantity INTEGER NOT NULL,
    price INTEGER NOT NULL,
    cart_id VARCHAR(45),
    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE
);

CREATE TABLE cart_discount (
id VARCHAR(45) NOT NULL,
amount INTEGER NOT NULL,
type VARCHAR(255),
individualUse BOOLEAN,
code VARCHAR(255),
cart_id VARCHAR(45),
FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE
);
