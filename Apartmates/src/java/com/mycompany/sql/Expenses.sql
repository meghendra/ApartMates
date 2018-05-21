/**
 * Author:  megh
 * Created: Nov 28, 2016
 */
# -------------------------------------------------------------
# SQL Script to Create The Expense Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS Expense;

CREATE TABLE Expense
(
    expense_ID INT PRIMARY KEY AUTO_INCREMENT,
    expense_name VARCHAR (255) NOT NULL,
    expense_amount DECIMAL (10,2) NOT NULL,
    expense_lender INT NOT NULL,
    expense_lendees VARCHAR (255) NOT NULL,
    expense_location VARCHAR (255),
    expense_details VARCHAR (255),
    expense_timestamp datetime,
    expense_is_settled VARCHAR (255) NOT NULL,
    apartment_ID INT NOT NULL REFERENCES Apartment(apartment_ID) ON DELETE CASCADE
);


