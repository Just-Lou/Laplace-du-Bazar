SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

CREATE SCHEMA app;

ALTER SCHEMA app OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE app.Scores
(
    scoreId UUID,
    score   FLOAT,
    number  INT,
    PRIMARY KEY (scoreId)
);

CREATE TABLE app.UsersTypes
(
    userTypeId UUID,
    userType   TEXT,
    PRIMARY KEY (userTypeId)
);

CREATE TABLE app.ApartmentSizes
(
    apartmentSizeId UUID,
    apartmentSize   TEXT,
    PRIMARY KEY (apartmentSizeId)
);

CREATE TABLE app.Categories
(
    categoryId UUID,
    category   TEXT,
    PRIMARY KEY (categoryId)
);

CREATE TABLE app.Programs
(
    programId UUID,
    program   TEXT,
    PRIMARY KEY (programId)
);

CREATE TABLE app.Users
(
    userId        UUID,
    firstName     TEXT,
    lastName      TEXT,
    email         TEXT,
    passwordHash  TEXT NOT NULL,
    phoneNumber   TEXT,
    creationDate  TIMESTAMP,
    enabled       BOOLEAN,
    scoreClientId UUID,
    userTypeId    UUID,
    scoreSellerId UUID,
    PRIMARY KEY (userId),
    UNIQUE (scoreClientId),
    UNIQUE (scoreSellerId),
    FOREIGN KEY (scoreClientId) REFERENCES app.Scores (scoreId),
    FOREIGN KEY (userTypeId) REFERENCES app.UsersTypes (userTypeId),
    FOREIGN KEY (scoreSellerId) REFERENCES app.Scores (scoreId)
);

CREATE TABLE app.Ads
(
    adId            UUID,
    title           TEXT,
    description     TEXT,
    folderPath      TEXT,
    publicationDate TIMESTAMP,
    flagQty         INT,
    price           FLOAT,
    userId          UUID,
    isArchived      BOOLEAN,
    PRIMARY KEY (adId),
    FOREIGN KEY (userId) REFERENCES app.Users (userId)
);

CREATE TABLE app.Apartments
(
    apartmentId     UUID,
    disponibility   TIMESTAMP,
    address         TEXT,
    apartmentSizeId UUID,
    adId            UUID,
    PRIMARY KEY (apartmentId),
    FOREIGN KEY (apartmentSizeId) REFERENCES app.ApartmentSizes (apartmentSizeId),
    FOREIGN KEY (adId) REFERENCES app.Ads (adId)
);

CREATE TABLE app.Books
(
    bookId     UUID,
    bookTitle  TEXT,
    author     TEXT,
    categoryId UUID,
    adId       UUID,
    PRIMARY KEY (bookId),
    FOREIGN KEY (categoryId) REFERENCES app.Categories (categoryId),
    FOREIGN KEY (adId) REFERENCES app.Ads (adId)
);

CREATE TABLE app.Equipments
(
    equipmentId UUID,
    categoryId  UUID,
    programID   UUID,
    adId        UUID,
    PRIMARY KEY (equipmentId),
    FOREIGN KEY (categoryId) REFERENCES app.Categories (categoryId),
    FOREIGN KEY (programId) REFERENCES app.Programs (programId),
    FOREIGN KEY (adId) REFERENCES app.Ads (adId)
);

CREATE TABLE app.Reports
(
    userId UUID,
    adId   UUID,
    PRIMARY KEY (userId, adId),
    FOREIGN KEY (userId) REFERENCES app.Users (userId),
    FOREIGN KEY (adId) REFERENCES app.Ads (adId)
);

CREATE TABLE app.Favorites
(
    userId UUID,
    adId   UUID,
    PRIMARY KEY (userId, adId),
    FOREIGN KEY (userId) REFERENCES app.USERS (userId),
    FOREIGN KEY (adId) REFERENCES app.Ads (adId)
);


-- ========== créer les données de base dans les tables ========== --
insert into app.apartmentsizes
values ('00000000-0000-0000-0000-000000000112', '1 1/2'),
       ('00000000-0000-0000-0000-000000000212', '2 1/2'),
       ('00000000-0000-0000-0000-000000000312', '3 1/2'),
       ('00000000-0000-0000-0000-000000000412', '4 1/2'),
       ('00000000-0000-0000-0000-000000000512', '5 1/2'),
       ('00000000-0000-0000-0000-000000000612', '6 1/2');

-- catégories de livres
truncate table app.categories cascade;
insert into app.categories
values  ('00000000-0000-0000-0000-000000000001', 'Narratif'),
        ('00000000-0000-0000-0000-000000000002', 'Électricité'),
        ('00000000-0000-0000-0000-000000000003', 'Chimie'),
        ('00000000-0000-0000-0000-000000000004', 'Thermodynamique'),
        ('00000000-0000-0000-0000-000000000005', 'Environnement'),
        ('00000000-0000-0000-0000-000000000006', 'Programmation');

-- catégories d'équipements
insert into app.categories
values ('00000000-0000-0000-0000-000000000000', 'Outils'),
       ('00000000-0000-0000-0000-000000000008', 'Équipement de protection'),
       ('00000000-0000-0000-0000-000000000009', 'Laboratoire');

insert into app.programs
values ('00000000-0000-0000-1111-000000000000', 'Génie biotechnologique'),
       ('00000000-0000-0000-2222-000000000000', 'Génie civil'),
       ('00000000-0000-0000-3333-000000000000', 'Génie chimique'),
       ('00000000-0000-0000-4444-000000000000', 'Génie du bâtiment'),
       ('00000000-0000-0000-5555-000000000000', 'Génie électrique'),
       ('00000000-0000-0000-6666-000000000000', 'Génie informatique'),
       ('00000000-0000-0000-7777-000000000000', 'Génie mécanique'),
       ('00000000-0000-0000-8888-000000000000', 'Génie robotique');




-- First create scores for clients and sellers
INSERT INTO app.Scores (scoreId, score, number)
VALUES ('11111111-1111-1111-1111-111111111111', 4.3, 10),
       ('22222222-2222-2222-2222-222222222222', 3.8, 5),
       ('33333333-3333-3333-3333-333333333333', 5.0, 15),
       ('44444444-4444-4444-4444-444444444444', 4.2, 8),
       ('55555555-5555-5555-5555-555555555555', 4.7, 12),
       ('66666666-6666-6666-6666-666666666666', 3.5, 3),
       ('77777777-7777-7777-7777-777777777777', 4.5, 20),
       ('88888888-8888-8888-8888-888888888888', 3.1, 50),
       ('99999999-9999-9999-9999-999999999999', 2.5, 7),
       ('77777777-7777-7777-7777-777777777771', 4.5, 34),
       ('77777777-7777-7777-7777-777777777772', 4.0, 18),
       ('77777777-7777-7777-7777-777777777773', 4.6, 21),
       ('77777777-7777-7777-7777-777777777774', 4.4, 33),
       ('77777777-7777-7777-7777-777777777775', 2.7, 9),
       ('77777777-7777-7777-7777-777777777776', 3.0, 4),
       ('77777777-7777-7777-7777-777777777778', 4.2, 28);


-- Create user types
INSERT INTO app.UsersTypes (userTypeId, userType)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'StandardUser'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'ExternalUser'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Administrator');

-- Create users
INSERT INTO app.Users (userId, firstName, lastName, email, passwordHash, phoneNumber, creationDate, enabled,
                       scoreClientId, userTypeId, scoreSellerId)
VALUES ('aaaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaaa', 'John', 'Doe', 'john.doe@example.com', 'hashedpassword1', '555-123-4567',
        '2023-01-15 08:30:00', TRUE, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        '44444444-4444-4444-4444-444444444444'),
       ('baaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaab', 'Jane', 'Smith', 'jane.smith@example.com', 'hashedpassword2', '555-987-6543',
        '2023-02-20 14:45:00', TRUE, '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        '55555555-5555-5555-5555-555555555555'),
       ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e3', 'Laplace', 'Admin', 'admin@laplace.com', 'motdepasse', '555-456-7890', '1970-01-01 00:00:01',
        true, '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
        '66666666-6666-6666-6666-666666666666'),
        ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e4', 'Maïna', 'Clermont', 'mclermont@gmail.com', 'hashedpassword3', '819-000-1234', '2010-05-05 18:18:01',
         true, '77777777-7777-7777-7777-777777777777', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777773'),
        ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e5', 'Amélie', 'Luneau', 'aluneau@gmail.com', 'hashedpassword4', '819-999-0000', '2020-08-08 18:18:02',
         true, '77777777-7777-7777-7777-777777777771', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777774'),
        ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e6', 'Gabriel', 'Bruneau', 'gbruneau@gmail.com', 'hashedpassword5', '819-888-2222', '2014-12-12 18:18:03',
         true, '88888888-8888-8888-8888-888888888888', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777772'),
        ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e7', 'Louis-Antoine', 'Gagnon', 'lagagnon@gmail.com', 'hashedpassword6', '819-675-6001', '2022-03-03 18:18:04',
         true, '77777777-7777-7777-7777-777777777778', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777776'),
        ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e8', 'Zakary', 'Romdhane', 'zromdhane@gmail.com', 'hashedpassword7', '819-111-3987', '2013-04-04 18:18:05',
         true, '99999999-9999-9999-9999-999999999999', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777775');


--tests pour les annonces
insert into app.ads (adid, title, description, publicationdate, price, userid, isarchived)
values ('aaaaaaaa-bbbb-aaaa-aaaa-aaaaaaaaaaaa', 'Location d''appartement', 'Un magnifique cinq et demi meublé vous attend tout près de l''université de Sherbrooke, avec chauffage et électricité inclus! Pour plus d''informations, n''hésitez pas à m''écrire.', '2023-01-15 08:30:16', 1300, 'aaaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaaa', false),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd4', 'Appartement à louer', 'Un petit deux et demi est à la recherche de locataires, il vous offre du chauffage et de l''électricité. Parfait pour un couple ou un étudiant qui étudie à l''université Bishop. Contactez-moi pour plus de détails.', '2024-05-02 10:44:08', 1200, 'aaaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaaa', false ),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd1', 'Appartement à louer', 'Bonjour, un beau quatre et demi est libre à partir du 18 mars 2025, bloc neuf de 3 ans, non meublé, inclusion : chauffage, électricité et internet, tout cela pour seulement 1500$ par mois. Contactez-moi si vous êtes intéressé!', '2024-09-20 17:51:45', 1500, '0d9fe705-30c3-4ce3-bfea-e03302fcf9e6', false),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd2', 'Chambre à louer', 'Une chambre meublée dans un grand six et demi est libre pour la session d''hiver 2026, il y aura trois autres locataires présents avec chauffage, électricité et internet inclus. Si vous êtes intéressé, contactez-moi!', '2025-02-10 13:47:38', 1600, '0d9fe705-30c3-4ce3-bfea-e03302fcf9e4', false),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd3', 'Appartement à louer', 'Un trois et demi est disponible à partir du mois de mai 2026, rien n''est inclus, mais il est à proximité de plusieurs épiceries et pharmacies.', '2025-05-30', 1250, '0d9fe705-30c3-4ce3-bfea-e03302fcf9e8',false),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd5', 'Recherche d''un(e) colloc', 'Moi et ma coloc sommes à la recherche d''un(e) troisième coloc pour combler la chambre vide dans notre splendide cinq et demi. Tu n''auras qu''à meubler ta chambre, le reste est déjà tout installé incluant chauffage, air climatisé, électricité et un adorable chat. Si jamais tu es intéressé, écris-moi!', '2025-01-13 16:33:58', 1650, '0d9fe705-30c3-4ce3-bfea-e03302fcf9e5', false),
       ('dddddddd-dddd-dddd-dddd-ddddddddddd6', 'Appartement à louer', 'Je cherche un(e) volontaire pour occuper mon un et demi pendant mon voyage de six mois de long. L''électricité, le chauffage, l''air climatisé puis l''internet sont inclus. L''appartement est également meublé et contient beaucoup de plantes. Écris-moi pour plus d''informations!', '2025-06-20 12:28:43',900, '0d9fe705-30c3-4ce3-bfea-e03302fcf9e7', false);

insert into app.apartments (apartmentid, disponibility, address, apartmentsizeid, adid)
values ('aaaaaaaa-bbbb-bbbb-aaaa-aaaaaaaaaaaa', '2023-01-15 08:30:00', '123 rue x, Sherbrooke', '00000000-0000-0000-0000-000000000512', 'aaaaaaaa-bbbb-aaaa-aaaa-aaaaaaaaaaaa'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', '2025-03-18 10:30:00', '456 rue y, Sherbrooke', '00000000-0000-0000-0000-000000000412', 'dddddddd-dddd-dddd-dddd-ddddddddddd1'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', '2026-01-01 08:00:00', '789 rue w, Sherbrooke', '00000000-0000-0000-0000-000000000612', 'dddddddd-dddd-dddd-dddd-ddddddddddd2'),
       ('ffffffff-ffff-ffff-ffff-fffffffffff1', '2026-05-01 07:00:00', '098 rue z, Sherbrooke', '00000000-0000-0000-0000-000000000312', 'dddddddd-dddd-dddd-dddd-ddddddddddd3'),
       ('ffffffff-ffff-ffff-ffff-fffffffffff2', '2024-07-10 09:00:00', '012 rue m, Sherbrooke', '00000000-0000-0000-0000-000000000212', 'dddddddd-dddd-dddd-dddd-ddddddddddd4'),
       ('ffffffff-ffff-ffff-ffff-fffffffffff3', '2025-12-16 15:00:00', '888 rue o, Sherbrooke', '00000000-0000-0000-0000-000000000512', 'dddddddd-dddd-dddd-dddd-ddddddddddd5'),
       ('ffffffff-ffff-ffff-ffff-fffffffffff4', '2026-02-25 18:00:00', '111 rue n, Sherbrooke', '00000000-0000-0000-0000-000000000112', 'dddddddd-dddd-dddd-dddd-ddddddddddd6');