/**
 * Author:  megh
 * Created: Nov 21, 2016
 */
# -------------------------------------------------------------
# SQL Script to Create The Roommates Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS Roommate;

CREATE TABLE Roommate
(
    roommate_ID INT PRIMARY KEY AUTO_INCREMENT,
    password VARCHAR (255) NOT NULL,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL,
    security_question INT NOT NULL, 
    security_answer VARCHAR (255) NOT NULL,
    apartment_ID INT UNSIGNED,
    points INT UNSIGNED DEFAULT 0
);