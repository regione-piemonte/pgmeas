INSERT INTO gmfprogrammi (`value`, `order`, active, tenant)
SELECT tmp.value, tmp.order, tmp.active, 10
FROM (VALUES
  ('NEXT GENERATION UE', 1, 1),
  ('URBACT IV', 2, 1),
  ('COESIONE TERRITORIALE', 3, 1),
  ('POR PIEMONTE FESR 2013-2020', 4, 1),
  ('PR FESR 2021-2027', 5, 1),
  ('PINQUA', 6, 1),
  ('AICS - AGENZIA ITALIANA COOPERAZIONE SVILUPPO', 7, 1),
  ('FINANZIAMENTI PRIVATI', 8, 1),
  ('PROGRAMMI REGIONE PIEMONTE', 9, 1)
) AS tmp(`value`, `order`, active)
WHERE NOT EXISTS (
  SELECT 1
  FROM gmfprogrammi curr
  WHERE curr.order = tmp.order
  AND curr.tenant = 10
);

INSERT INTO gmffondo (`value`, `order`, active, tenant, type_fund)
SELECT tmp.value, tmp.order, tmp.active, tmp.type_fund, 10
FROM (VALUES
  ('PNRR', 1, 1, 2),
  ('L n.145/2018 ', 2, 1, 2),
  ('GSE', 3, 1, 2),
  ('D.L. 34/2019 ', 4, 1, 2),
  ('FOI', 5, 1, 2),
  ('L n.160/2019', 6, 1, 2),
  ('GRANDI BANDI', 7, 1, 2),
  ('NEXT GENERATION WE', 8, 1, 2),
  ('BANDO CORPI DIRCI 2023', 9, 1, 2)
) AS tmp(`value`, `order`, active, type_fund)
WHERE NOT EXISTS (
  SELECT 1
  FROM gmffondo curr
  WHERE curr.order = tmp.order
  AND curr.tenant = 10
);

INSERT INTO gmfstrutturadiriferimento (`value`, `order`, active, tenant, type_fund)
SELECT tmp.value, tmp.order, tmp.active, tmp.type_fund, 10
FROM (VALUES
  ('REGIONE PIEMONTE', 1, 1),
  ('CITTA METROPOLITANA DI TORINO', 2, 1),
) AS tmp(`value`, `order`, active, type_fund)
WHERE NOT EXISTS (
  SELECT 1
  FROM gmfstrutturadiriferimento curr
  WHERE curr.order = tmp.order
  AND curr.tenant = 10
);
