CREATE TABLE ingresso (
    id SERIAL PRIMARY KEY,
    compra_id INTEGER NOT NULL,
    sessao_assento_id INTEGER NOT NULL UNIQUE,
    is_inteira BOOLEAN NOT NULL DEFAULT TRUE,
    valor_pago NUMERIC(10, 2) NOT NULL,
    CONSTRAINT fk_ingresso_compra FOREIGN KEY (compra_id) REFERENCES compra(id),
    CONSTRAINT fk_ingresso_sessao_assento FOREIGN KEY (sessao_assento_id) REFERENCES sessao_assento(id)
);
