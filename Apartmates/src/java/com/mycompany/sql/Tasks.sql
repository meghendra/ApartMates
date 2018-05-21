/**
 * Author:  megh
 * Created: Nov 28, 2016
 */
# -------------------------------------------------------------
# SQL Script to Create The Task Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS Task;

CREATE TABLE Task
(
    task_ID INT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR (32) NOT NULL,
    task_details VARCHAR (128) NOT NULL,
    task_location VARCHAR (128) NOT NULL,
    task_deadline datetime,
    task_reminder1 datetime,
    task_reminder2 datetime,
    task_reminder3 datetime,
    task_priority VARCHAR (32) NOT NULL,
    task_is_complete VARCHAR (32) NOT NULL,
    apartment_ID INT NOT NULL REFERENCES Apartment(apartment_ID) ON DELETE CASCADE
);