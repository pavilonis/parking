INSERT INTO PARKING_LOT (name, size)
VALUES ('Lot A', 100),
       ('Lot B', 80);

INSERT INTO COMPANY (name)
VALUES ('Company X'),
       ('Company Y');

INSERT INTO PARKING_LOT_COMPANY_SPACES (parking_lot_id, company_id, spaces)
VALUES ((SELECT id FROM PARKING_LOT WHERE name = 'Lot A'), (SELECT id FROM COMPANY WHERE name = 'Company X'), 30),
       ((SELECT id FROM PARKING_LOT WHERE name = 'Lot A'), (SELECT id FROM COMPANY WHERE name = 'Company Y'), 40);