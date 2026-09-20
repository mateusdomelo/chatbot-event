CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_number INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending'
);