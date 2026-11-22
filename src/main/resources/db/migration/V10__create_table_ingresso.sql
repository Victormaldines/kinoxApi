CREATE TABLE ingresso (
      id SERIAL PRIMARY KEY,
      sessao_id BIGINT NOT NULL,
      inteira BOOLEAN NOT NULL DEFAULT TRUE,
      CONSTRAINT fk_ingresso_sessao FOREIGN KEY (sessao_id) REFERENCES sessao(id)
);