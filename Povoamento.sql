
SELECT * FROM trader;
SELECT * FROM cfd;
SELECT * FROM ativo;

DELETE FROM trader;
DELETE FROM ativo WHERE id_ativo > '0';
DELETE FROM cfd;

INSERT INTO ativo
VALUES -- ('AAPL', 'Apple', 'acao'),
	   ('AMZN', 'Amazon', 'acao'),
       ('GOLD', 'Gold', 'comodity');
       
SELECT * FROM cfd WHERE trader_id = 1;