CREATE TABLE SUBSCRIPTION (
    id serial NOT NULL,
    msisdn bigint NOT NULL,
    timestamp bigint NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE PURCHASE (
    id serial NOT NULL,
    msisdn bigint NOT NULL,
    timestamp bigint NOT NULL,
    PRIMARY KEY (ID)
);