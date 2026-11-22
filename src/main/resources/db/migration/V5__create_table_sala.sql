CREATE TABLE sala (
    id serial PRIMARY KEY,
    numero INTEGER,
    preco_do_ingresso DOUBLE PRECISION,
    qtd_assentos INTEGER,
    is_3d BOOLEAN DEFAULT FALSE NOT NULL,
    is_disponivel BOOLEAN DEFAULT TRUE NOT NULL,
    is_legendado BOOLEAN DEFAULT FALSE NOT NULL,
    is_imax BOOLEAN DEFAULT FALSE NOT NULL
);