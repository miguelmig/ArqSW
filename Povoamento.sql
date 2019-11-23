
SELECT * FROM trader;
SELECT * FROM cfd;
SELECT * FROM ativo;

DELETE FROM trader;
DELETE FROM ativo;
DELETE FROM cfd;

INSERT INTO ativo
VALUES ('AAPL', 'Apple', 'acao'),
	   ('AMZN', 'Amazon', 'acao'),
       ('GOLD', 'Gold', 'comodity');