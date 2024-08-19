CREATE TABLE IF NOT EXISTS pedido
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(255)   NOT NULL UNIQUE,
    data_cadastro   DATE,
    nome            VARCHAR(255)   NOT NULL,
    valor           DECIMAL(10, 2) NOT NULL,
    quantidade      INT DEFAULT 1,
    valor_total     DECIMAL(10, 2),
    codigo_cliente  INT            NOT NULL
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;