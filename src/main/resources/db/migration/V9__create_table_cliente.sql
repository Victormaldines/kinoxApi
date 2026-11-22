CREATE TABLE cliente (
     id SERIAL PRIMARY KEY,
     cpf VARCHAR(14) NOT NULL UNIQUE,
     met_pagamento VARCHAR(50),
     nome VARCHAR(150) NOT NULL,
     nascimento VARCHAR(20),
     senha VARCHAR(255) NOT NULL,
     email VARCHAR(150) NOT NULL UNIQUE
);