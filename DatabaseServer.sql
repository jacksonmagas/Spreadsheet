CREATE DATABASE spreadsheetProject;
USE spreadsheetProject;

create table users
(
username varchar(12) primary key,
userPassword varchar(10) not null,
name_u varchar(32) not null
);

create table spreadsheet
(
spreadsheetNumber int primary key,
s_name varchar(32) not null,
created_by varchar(12) not null,
created_date DATE not null,
FOREIGN KEY (created_by) REFERENCES users(username)
ON UPDATE CASCADE ON DELETE RESTRICT
);

create table worksheet
(
worksheet_id int primary key,
spreadsheet_id int not null,
name varchar(32) not null,
num_rows int not null,
num_cols int not null,
FOREIGN KEY (spreadsheet_id) REFERENCES spreadsheet(spreadsheetNumber)
ON UPDATE CASCADE ON DELETE RESTRICT
);

create table cells
(
cell_id int primary key,
worksheet_id int not null,
row_num int not null,
col_num int not null,
cell_type varchar(32),
content TEXT,
FOREIGN KEY (worksheet_id) REFERENCES worksheet(worksheet_id)
ON UPDATE CASCADE ON DELETE RESTRICT
);

create table lastEdit
(
username varchar(12),
spreadsheetNumber int,
last_edit_date DATE,
last_edit_time TIME,
PRIMARY KEY (username, spreadsheetNumber),
FOREIGN KEY (username) REFERENCES users(username)
ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY (spreadsheetNumber) REFERENCES spreadsheet(spreadsheetNumber)
ON UPDATE CASCADE ON DELETE RESTRICT
);

create table get_permission
(
permissionId int primary key,
username varchar(12) not null,
spreadsheetId int not null,
type_of_permission enum('view', 'edit', 'owner'),
FOREIGN KEY (username) REFERENCES users(username)
ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY (spreadsheetId) REFERENCES spreadsheet(spreadsheetNumber)
ON UPDATE CASCADE ON DELETE RESTRICT
);

DROP PROCEDURE IF EXISTS create_huskeysheet_user;
DELIMITER $$
create procedure create_husky_sheet_user(
IN username_n varchar(12), 
IN password_n varchar(10),
IN name_n varchar(32))

BEGIN
   IF (SELECT COUNT(*) FROM users WHERE username = username_n) > 0 THEN
    SELECT 'User is already in system' as message;

    ELSE
  INSERT INTO users(username, userPassword, name_u) VALUES (username_n, password_n, name_n);
   
   end if;
   SELECT * FROM users;
   END$$

call create_husky_sheet_user('winkleblackk', '1234567898', 'Katie');

call create_husky_sheet_user('username1234', '1234567899', 'Julia');

/* Throws the proper error for a password being too long*/
call create_husky_sheet_user('invalidpass1', '12345678999', 'Grace');

/* Test to make sure Mark can not be added because the username already exists*/
call create_husky_sheet_user('winkleblackk', '3344667788', 'Mark');

call create_husky_sheet_user('gooduser1234', '3344667788', 'Mark');
