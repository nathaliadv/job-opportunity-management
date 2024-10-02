INSERT INTO testdb.public.candidate (id,created_at,curriculum,description,email,name,password,username) VALUES
 ('233a64bf-95bb-460a-a809-9e9085bdbf28','2024-09-17 20:51:12.94158',NULL,'Software Developer Junior','maria-santos@gmail.com','Maria Santos','$2a$10$fmCamx1f7WxyU54G1LL4l.sgRj4JLH/IgC.N8Xx1MXG7OUmq984j2','masantos'),
 ('b2391018-4671-4b34-9b6e-a902e797de9a','2024-09-17 21:02:46.970848',NULL,'Software Engineer Senior','jorge-silva@gmail.com','Jorge Silva','$2a$10$nIDSfyTBn6KmLzaBChnRZeoFySxqbgBQpXBc0rXiUibKkJvK/ebdW','jorgesilva'),
 ('0b96d6ee-efec-4bc0-b6a4-f304591b3d0f','2024-09-21 20:05:57.143185',NULL,'Java Software Engineer with 3 years of experience.','nathdviana@gmail.com','Nathalia Viana','$2a$10$j/iCRuR2TGVCgadyhRoqruTR7kliXmh15rRSiV2yCENhrYcA/jwee','nathdviana'),
 ('7c94b6f5-4379-4ee4-a4ff-3b51f165c32a','2024-09-22 12:31:12.068685',NULL,'Java Software Engineer with 5 years of experience.','marcos_maia@outlook.com','Marcos Maia','$2a$10$BPYo1NaCONdup5Zt0ZZL7e9L.HboF4rI6Mo572/jFNB4IsuAAL6CK','marcos-maia'),
 ('4e529180-5457-4145-bc25-8ab903849478','2024-09-22 12:43:17.118109',NULL,'Java Software Developer','jc_souza@outlook.com','José Carlos Souza','$2a$10$..XRXqx2w/HwwexVCFakE.gDeRO8l.KJaxiJwaorTy5Lz/L35HbMm','jcsouza');

INSERT INTO testdb.public.company (id,created_at,description,email,name,password,username,website) VALUES
('b0c429a1-38d1-47e5-ac9a-8a7a2bfe3fd3','2024-09-17 20:58:55.383061','Soluções Tech para Gestão de Caixa','admin_rh@techsolutions.com','Tech Solutions','$2a$10$HQXdsz2HhstmX.16dYyqbOfhOZSt3pa81Saupe353AIyVaUV4PHHe','admin-tech-solutions','www.techsolutions.com'),
('3d1ecbaf-9060-4646-949c-7490640f1e25','2024-09-17 21:01:31.440916','Java solutions for your business','admin_rh@techjava.com','Tech Java','$2a$10$ZF8Hz3jghyjrY1u4Lo9yHOm7km16boBdeoJqW8pyXWuWHk5qoY6dq','admin-tech-java','www.techjava.com');

INSERT INTO testdb.public.job (id,benefits,company_id,created_at,description,level) VALUES
('b8c7ae4f-8f1d-45d6-8c8b-ca93853fb82f','paid vacation; health plan;','b0c429a1-38d1-47e5-ac9a-8a7a2bfe3fd3','2024-09-17 20:59:38.424186','QA Tester','Junior'),
('62415720-dcfc-485b-87ca-5790a702afd8','paid vacation; health plan;','b0c429a1-38d1-47e5-ac9a-8a7a2bfe3fd3','2024-09-21 18:51:46.760322','Software Developer','Mid Level'),
('0b7eb246-842b-49b6-9c33-ea65b8b9e150','paid vacation; health plan;','3d1ecbaf-9060-4646-949c-7490640f1e25','2024-09-21 18:54:38.420125','Backend Java','Mid Level');

INSERT INTO testdb.public.job_application (id,candidate_id,created_at,job_id) VALUES
('101e6015-2b85-4e02-89a5-a3b6f9e59595','0b96d6ee-efec-4bc0-b6a4-f304591b3d0f','2024-09-24 20:50:23.90786','0b7eb246-842b-49b6-9c33-ea65b8b9e150'),
('91387317-3d42-49f3-8e31-ad05ebda8f12','7c94b6f5-4379-4ee4-a4ff-3b51f165c32a','2024-09-24 21:14:31.189436','62415720-dcfc-485b-87ca-5790a702afd8');
