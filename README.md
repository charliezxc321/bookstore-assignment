# Bookstore-assignment
A simple bookstore for users to upload and retrieve their books

## Requirements ##
Please setup a local mysql service, on port 3306.
Please refer to script db_setup.mysql to run the init commands before starting the application. \
	- create a user bookstore-admin, with password bookstore-admin. \
	- create a schema "book_test" 

## Add a new book ##
curl --location 'localhost:8080/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bm9ybWFsX3VzZXI6dGVzdDMyMQ==' \
--header 'Cookie: JSESSIONID=B3A20C8671512794FA838597F32F6476' \
--data '{
    "title":"The Lord of the Rings",
    "authors":[1],
    "year": 2024,
    "price": 20.80,
    "genre":"Adventure"

}'

## Update book ##
curl --location --request PUT 'localhost:8080/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bm9ybWFsX3VzZXI6dGVzdDMyMQ==' \
--header 'Cookie: JSESSIONID=B3A20C8671512794FA838597F32F6476' \
--data '{
    "title": "The Lord of the Rings",
    "isbn": "9780065461868",
    "authors": [1],
    "year": 2024,
    "price": 80.80,
    "genre": "Horror"
}'
## Find books by title and/or author ##
curl --location 'localhost:8080/books/search?title=The%20Lord%20of%20the%20Rings&author=J.K.%20Rowling&searchType=AND' \
--header 'title: Moby-Dick' \
--header 'Authorization: Basic bm9ybWFsX3VzZXI6dGVzdDMyMQ==' \
--header 'Cookie: JSESSIONID=B3A20C8671512794FA838597F32F6476' \
--data ''

## Delete book ##
curl --location --request DELETE 'localhost:8080/books?isbn=9780065461868' \
--header 'Authorization: Basic YWRtaW5fdXNlcjp0ZXN0MTIz' \
--header 'Cookie: JSESSIONID=B3A20C8671512794FA838597F32F6476'


## Roles and Credentials ##
Role: ADMIN \
Username: admin_user \
Password: test123

Role: USER \
Username: normal_user \
Password: test321
