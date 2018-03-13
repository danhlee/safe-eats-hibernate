# Daniel Lee
# Safe Eats
CREATE DATABASE safeEats_db;
GRANT ALL PRIVILEGES ON safeEats_db.* TO 'student'@'localhost';
FLUSH PRIVILEGES;
USE safeEats_db;

CREATE TABLE Inspectors(
	inspectorID int NOT NULL PRIMARY KEY,
    numInspections int
);

CREATE TABLE Users(
	username varchar(20) NOT NULL PRIMARY KEY,
    pass varchar(40),
    firstName varchar(20),
    lastName varchar(20),
    inspectorID int,
    FOREIGN KEY (inspectorID) 
		REFERENCES Inspectors(inspectorID)
);

CREATE TABLE Restaurants(
	restID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    restName varchar(150),
    restAddress varchar(150)
);

ALTER TABLE Restaurants AUTO_INCREMENT = 100000;

CREATE TABLE Inspections(
	inspection_ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    inspectorID int,
    restID int,
    inspectionDate TIMESTAMP,
    risk varchar(4),
    results varchar(4),
    FOREIGN KEY (inspectorID) 
		REFERENCES Inspectors(inspectorID),
    FOREIGN KEY (restID) 
		REFERENCES Restaurants(restID)
		ON DELETE CASCADE
);

-- insert sample inspector
INSERT INTO Inspectors() VALUES 
	(1, 0),
	(2, 0),
    (3, 0);

-- users that can log into app
INSERT INTO Users() VALUES 
	('inspector1', SHA('inspector1'), 'Jimi', 'Hendrix', 1),
	('inspector2', SHA('inspector2'), 'Mitch', 'Mitchell', 2),
    ('jdoe', SHA('jdoe'), 'John', 'Doe', 3);
    
-- insert sample basic user
INSERT INTO Users() VALUES ('user1', SHA('user1'), 'Eric', 'Clapton', NULL);

-- insert sample restaurants
INSERT INTO Restaurants(restName, restAddress) VALUES
	('DUNKIN DONUTS/BASKIN ROBBINS', '1982 N CLYBOURN AVE'),
    ('DELICIOUS MOMENT', '2002 S WENTWORTH AVE'),
    ('NOT JUST COOKIES BAKERY', '901 S PLYMOUTH CT'),
    ('PIZZA HUT', '5240 N PULASKI RD');
-- insert sample inspections
INSERT INTO Inspections(inspectorID, restID, risk, results) VALUES
	(1, 100000, 'MED', 'PASS'),
    (1, 100001, 'HIGH', 'FAIL'),
    (2, 100002, 'LOW', 'PASS'),
	(2, 100003, 'MED', 'FAIL');
    
-- manually set for sample entries (will auto-update if entries are inserted via the APP)
UPDATE Inspectors SET numInspections=2 WHERE inspectorID=1;
UPDATE Inspectors SET numInspections=2 WHERE inspectorID=2;