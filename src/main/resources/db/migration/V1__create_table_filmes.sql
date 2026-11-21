CREATE TABLE filme (
    id serial PRIMARY KEY,
    duracao INTEGER,
    sinopse text,
    nome VARCHAR NOT NULL,
    classificacao_indicativa INTEGER,
    diretor VARCHAR,
    imagem_url VARCHAR,
    created_at DATE,
    updated_at DATE
);
