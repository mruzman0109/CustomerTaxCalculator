-- TAX CONFIG for London
INSERT INTO tax_config (id, city, max_daily_fee) VALUES (1, 'London', 15);

-- TAX RULES for London
-- Weekdays: 07:00–18:00
INSERT INTO tax_rules (start_time, end_time, amount, tax_config_id) VALUES ('07:00', '18:00', 15, 1);
-- Weekends: 12:00–18:00
INSERT INTO tax_rules (start_time, end_time, amount, tax_config_id) VALUES ('12:00', '18:00', 15, 1);

-- EXEMPT VEHICLES for London
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Electric Vehicle');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Motorbike');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Moped');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Bicycle');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Blue Badge Holder');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Taxi');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Private Hire Vehicle');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Vehicle with 9+ Seats');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Roadside Recovery Vehicle');
INSERT INTO exempt_vehicles (tax_config_id, name) VALUES (1, 'Accredited Breakdown Vehicle');
