CREATE TABLE customer (
 id bigserial PRIMARY KEY,
 name VARCHAR(200) NOT NULL
);

CREATE TABLE carrier (
 id bigserial PRIMARY KEY,
 name VARCHAR(200) NOT NULL
);

CREATE TABLE bill (
 id bigserial PRIMARY KEY,
 customer_id BIGINT NOT NULL,
 carrier_id BIGINT NOT NULL,
 identifier VARCHAR(50) NOT NULL,
 year_month VARCHAR(7) NOT NULL,
 total NUMERIC(19, 2) NOT NULL,
 CONSTRAINT bill_uk UNIQUE (customer_id, identifier, year_month),
 CONSTRAINT bill_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer(id),
 CONSTRAINT bill_carrier_id_fk FOREIGN KEY (carrier_id) REFERENCES carrier(id)
);

CREATE TABLE bill_item (
 id bigserial PRIMARY KEY,
 bill_id BIGINT NOT NULL,
 date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
 description VARCHAR(200) NOT NULL,
 origin_number VARCHAR(20) NOT NULL,
 destination_number VARCHAR(20),
 duration BIGINT,
 value NUMERIC(19, 2) NOT NULL,
 type VARCHAR(50) NOT NULL,
 CONSTRAINT bill_item_bill_id_fk FOREIGN KEY (bill_id) REFERENCES bill(id)
);