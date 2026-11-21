CREATE TABLE filmes (
    idFilme serial PRIMARY KEY,
    duracao INTEGER,
    sinopse text,
    nome VARCHAR NOT NULL,
    classificacaoIndicativa INTEGER,
    diretor VARCHAR,
    imagem_url VARCHAR
);
