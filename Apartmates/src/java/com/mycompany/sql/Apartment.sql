
# -------------------------------------------------------------
# SQL Script to Create The Apartment Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS Apartment;

CREATE TABLE Apartment
(
    apartment_ID INT PRIMARY KEY AUTO_INCREMENT,
    apartmentName VARCHAR (255) NOT NULL,
    apartmentAddress VARCHAR (255) 
);
