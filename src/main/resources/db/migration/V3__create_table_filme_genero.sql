CREATE TABLE filme_genero (
    filme_id INTEGER,
    genero_id INTEGER,
    CONSTRAINT fk_filme_genero_filme FOREIGN KEY(filme_id) REFERENCES filme(id),
    CONSTRAINT fk_filme_genero_genero FOREIGN KEY(genero_id) REFERENCES genero(id)
);