
SELECT * FROM trader;
SELECT * FROM cfd;
SELECT * FROM ativo;

DELETE FROM trader;
DELETE FROM ativo WHERE id_ativo > '0';
DELETE FROM cfd WHERE id_cfd > 0;

INSERT INTO trader VALUE (1, 'teste', 'teste', 1000, 'teste');

INSERT INTO ativo
VALUES ('AAPL', 'Apple', 'acao'),
	   ('BTC', 'Bitcoin', 'crypto'),
       ('GOLD', 'Gold', 'comodity');
       
SELECT * FROM cfd WHERE trader_id = 1;