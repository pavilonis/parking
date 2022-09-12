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

CREATE TABLE PARKING_LOT_COMPANY_NUMBER_PLATES (
    parking_lot_id INT          NOT NULL,
    company_id     INT          NOT NULL,
    number_plate   VARCHAR (10) NOT NULL,
    PRIMARY KEY (parking_lot_id, company_id, number_plate),
    FOREIGN KEY (parking_lot_id) REFERENCES PARKING_LOT (id),
    FOREIGN KEY (company_id) REFERENCES COMPANY (id)
);

CREATE TABLE PARKING_EVENT (
    parking_lot_id INT          NOT NULL,
    number_plate   VARCHAR (10) NOT NULL,
    event_type     VARCHAR (10)  NOT NULL,
    created        DATETIME     NOT NULL DEFAULT NOW(),
    FOREIGN KEY (parking_lot_id) REFERENCES PARKING_LOT (id)
);