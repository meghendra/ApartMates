/**
 * Author:  megh
 * Created: Nov 28, 2016
 */
# -------------------------------------------------------------
# SQL Script to Create The Photo Table
# -------------------------------------------------------------

CREATE TABLE Photo
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
       roommate_id INT,
       FOREIGN KEY (roommate_id) REFERENCES Roommate(roommate_ID) ON DELETE CASCADE
);