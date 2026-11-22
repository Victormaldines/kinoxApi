ALTER TABLE sala
    DROP COLUMN IF EXISTS is_legendado,
    ALTER COLUMN preco_do_ingresso TYPE NUMERIC(10, 2) USING preco_do_ingresso::NUMERIC(10, 2);

ALTER TABLE sessao
    ADD COLUMN is_legendado BOOLEAN DEFAULT FALSE NOT NULL,
    ALTER COLUMN horario_inicio TYPE TIME
    USING (
        CASE
            WHEN horario_inicio IS NULL THEN NULL
            WHEN horario_inicio BETWEEN 0 AND 23 THEN make_time(horario_inicio, 0, 0)
            WHEN horario_inicio BETWEEN 100 AND 2359 AND (horario_inicio % 100) < 60
                THEN make_time(horario_inicio / 100, horario_inicio % 100, 0)
            ELSE TIME '00:00:00'
        END
    );

ALTER TABLE assento
    ADD COLUMN sala_id INTEGER,
    ADD CONSTRAINT fk_assento_sala FOREIGN KEY (sala_id) REFERENCES sala(id);

ALTER TABLE sessao_assento
    ADD CONSTRAINT uq_sessao_assento UNIQUE (sessao_id, assento_id);
