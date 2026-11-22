CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    valor_total NUMERIC(10, 2) NOT NULL,
    met_pagamento VARCHAR(50) NOT NULL,
    data_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_compra_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
