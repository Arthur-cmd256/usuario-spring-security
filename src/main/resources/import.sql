INSERT INTO tb_usuario (NOME, EMAIL, SENHA) VALUES ( 'ADMIN', 'admin@example.com', '$2a$12$v5Lr69vtKsMkn2BUH9Huce8IFMo1qc25EmczERxQmKG9RLvdRq4Cm');
INSERT INTO tb_perfil (NOME) VALUES ('ADMIN');
INSERT INTO tb_perfil (NOME) VALUES ('USER');
INSERT INTO tb_usuario_perfil (USUARIO_ID, PERFIL_ID) VALUES (1, 1);