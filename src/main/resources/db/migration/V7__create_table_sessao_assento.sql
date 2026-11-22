CREATE TABLE sessao_assento(
    id serial PRIMARY KEY,
    sessao_id INTEGER NOT NULL,
    assento_id INTEGER NOT NULL,
    is_ocupado BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT fk_sessao_assento_sessao FOREIGN KEY(sessao_id) REFERENCES sessao(id),
    CONSTRAINT fk_sessao_assento_assento FOREIGN KEY(assento_id) REFERENCES assento(id)
);