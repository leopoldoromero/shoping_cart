CREATE TABLE carts (
    id CHAR(36) NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    customerId CHAR(36) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart_items (
    id CHAR(36) NOT NULL,
    quantity INTEGER NOT NULL,
    price DOUBLE  NOT NULL,
    cartId CHAR(36),
    PRIMARY KEY (id),
    FOREIGN KEY (cartId) REFERENCES carts (id) ON DELETE CASCADE,
    INDEX (cartId)
);

CREATE TABLE cart_discount (
    id CHAR(36) NOT NULL,
    amount DOUBLE  NOT NULL,
    individualUse BOOLEAN,
    code VARCHAR(255),
    cartId CHAR(36),
    PRIMARY KEY (id),
    FOREIGN KEY (cartId) REFERENCES carts (id) ON DELETE CASCADE,
    INDEX (cartId)
);
