CREATE TABLE PARKING_LOT (
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    size INT         NOT NULL
);

CREATE TABLE COMPANY (
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE PARKING_LOT_COMPANY_SPACES (
    parking_lot_id   INT NOT NULL,
    company_id       INT NOT NULL,
    spaces           INT NOT NULL,
    PRIMARY KEY (parking_lot_id, company_id),
    FOREIGN KEY (parking_lot_id) REFERENCES PARKING_LOT (id),
    FOREIGN KEY (company_id) REFERENCES COMPANY (id)
);