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

CREATE TABLE app.Users (
    id uuid primary key,
    firstName text,
    lastName text,
    email text,
    passwordHash text
);

ALTER TABLE app.Users OWNER TO postgres;

INSERT INTO app.Users(id, firstName, lastName, email, passwordHash) VALUES ('25bbf038-ca4e-40bf-84bf-bb8258f56aca', 'Anatold', 'Mitoukilmailleself', 'anatold.emo@myspace.com','cc17a2f35ce65d2441ca0e7a169fb5effef20b03e778fab691acd1cf69744b256780440ac9c13575b2ba44db87e38b7ca1cc5f5d521809a536128c6ca1dbcd29');

INSERT INTO app.Users(id, firstName, lastName, email, passwordHash) VALUES ('f793e293-2b4a-4760-8cda-488c789522ca', 'Georges', 'Beaupelage', 'georgestropcool2008@yahoo.com','cc17a2f35ce65d2441ca0e7a169fb5effef20b03e778fab691acd1cf69744b256780440ac9c13575b2ba44db87e38b7ca1cc5f5d521809a536128c6ca1dbcd29');