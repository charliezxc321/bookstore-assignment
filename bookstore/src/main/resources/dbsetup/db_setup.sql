CREATE DATABASE book_test;
CREATE USER 'bookstore-admin'@'%' IDENTIFIED BY 'bookstore-admin';
GRANT ALL PRIVILEGES ON book_test.* TO 'bookstore-admin'@'%';
FLUSH PRIVILEGES;
