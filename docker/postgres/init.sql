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
values ('00000000-0000-0000-0000-000000000312', '3 1/2'),
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
VALUES ('11111111-1111-1111-1111-111111111111', 4.5, 10),
       ('22222222-2222-2222-2222-222222222222', 3.8, 5),
       ('33333333-3333-3333-3333-333333333333', 5.0, 15),
       ('44444444-4444-4444-4444-444444444444', 4.2, 8),
       ('55555555-5555-5555-5555-555555555555', 4.7, 12),
       ('66666666-6666-6666-6666-666666666666', 3.5, 3);


-- Create user types
INSERT INTO app.UsersTypes (userTypeId, userType)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'StandardUser'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'ExternalUser'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Administrator');

-- Create the dummy users
INSERT INTO app.Users (userId, firstName, lastName, email, passwordHash, phoneNumber, creationDate, enabled,
                       scoreClientId, userTypeId, scoreSellerId)
VALUES ('aaaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaaa', 'John', 'Doe', 'john.doe@example.com', 'hashedpassword1', '555-123-4567',
        '2023-01-15 08:30:00', TRUE, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        '44444444-4444-4444-4444-444444444444'),
       (gen_random_uuid(), 'Jane', 'Smith', 'jane.smith@example.com', 'hashedpassword2', '555-987-6543',
        '2023-02-20 14:45:00', TRUE, '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        '55555555-5555-5555-5555-555555555555'),
       ('0d9fe705-30c3-4ce3-bfea-e03302fcf9e3', 'Laplace', 'Admin', 'admin@laplace.com', 'motdepasse', '555-456-7890', '1970-01-01 00:00:01',
        true, '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc',
        '66666666-6666-6666-6666-666666666666');

--tests pour les annonces
insert into app.ads (adid, title, description, publicationdate, price, userid, isarchived)
values ('aaaaaaaa-bbbb-aaaa-aaaa-aaaaaaaaaaaa', 'Annonce 1', 'description annonce 1', '2023-01-15 08:30:00', 1300, 'aaaaaaaa-bbbb-bbbb-bbbb-aaaaaaaaaaaa', false);

insert into app.apartments (apartmentid, disponibility, address, apartmentsizeid, adid)
values ('aaaaaaaa-bbbb-bbbb-aaaa-aaaaaaaaaaaa', '2023-01-15 08:30:00', '123 rue x, sherbrooke', '00000000-0000-0000-0000-000000000612', 'aaaaaaaa-bbbb-aaaa-aaaa-aaaaaaaaaaaa');