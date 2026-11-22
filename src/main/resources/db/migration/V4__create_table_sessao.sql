CREATE TABLE sessao (
    id serial PRIMARY KEY,
    filme_id INTEGER,
    disponibilidade BOOLEAN DEFAULT TRUE NOT NULL,
    data DATE,
    horario_inicio INTEGER
);