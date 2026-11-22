CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    ingresso_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_compra_ingresso FOREIGN KEY (ingresso_id) REFERENCES ingresso(id),
    CONSTRAINT fk_compra_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);