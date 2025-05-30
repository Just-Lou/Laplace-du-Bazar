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

CREATE TABLE app.Scores(
                       scoreId UUID,
                       score FLOAT,
                       number INT,
                       PRIMARY KEY(scoreId)
);

CREATE TABLE app.UsersTypes(
                           userTypeId UUID,
                           userType TEXT,
                           PRIMARY KEY(userTypeId)
);

CREATE TABLE app.ApartmentSizes(
                               apartmentSizeId UUID,
                               apartmentSize TEXT,
                               PRIMARY KEY(apartmentSizeId)
);

CREATE TABLE app.Categories(
                           categoryId UUID,
                           category TEXT,
                           PRIMARY KEY(categoryId)
);

CREATE TABLE app.Users(
                      userId UUID,
                      firstName TEXT,
                      lastName TEXT,
                      email TEXT,
                      passwordHash TEXT,
                      phoneNumber TEXT,
                      creationDate TIMESTAMP,
                      enabled BOOLEAN,
                      scoreClientId UUID,
                      userTypeId UUID,
                      scoreSellerId UUID,
                      PRIMARY KEY(userId),
                      UNIQUE(scoreClientId),
                      UNIQUE(scoreSellerId),
                      FOREIGN KEY(scoreClientId) REFERENCES app.Scores(scoreId),
                      FOREIGN KEY(userTypeId) REFERENCES app.UsersTypes(userTypeId),
                      FOREIGN KEY(scoreSellerId) REFERENCES app.Scores(scoreId)
);

CREATE TABLE app.Ads(
                    adId UUID,
                    title TEXT,
                    description TEXT,
                    folderPath TEXT,
                    publicationDate TIMESTAMP,
                    flagQty INT,
                    price FLOAT,
                    userId UUID,
                    PRIMARY KEY(adId),
                    FOREIGN KEY(userId) REFERENCES app.Users(userId)
);

CREATE TABLE app.Apartments(
                           apartmentId UUID,
                           disponibility TIMESTAMP,
                           address TEXT,
                           apartmentSizeId UUID,
                           adId UUID,
                           PRIMARY KEY(apartmentId),
                           FOREIGN KEY(apartmentSizeId) REFERENCES app.ApartmentSizes(apartmentSizeId),
                           FOREIGN KEY(adId) REFERENCES app.Ads(adId)
);

CREATE TABLE app.Books(
                      bookId UUID,
                      bookTitle TEXT,
                      author TEXT,
                      categoryId UUID,
                      adId UUID,
                      PRIMARY KEY(bookId),
                      FOREIGN KEY(categoryId) REFERENCES app.Categories(categoryId),
                      FOREIGN KEY(adId) REFERENCES app.Ads(adId)
);