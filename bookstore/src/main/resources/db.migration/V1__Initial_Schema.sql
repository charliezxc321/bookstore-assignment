CREATE TABLE book (
  id                    BIGINT NOT NULL AUTO_INCREMENT,
  isbn                  VARCHAR(13) NOT NULL,
  title                 VARCHAR(50) NOT NULL,
  year_of_publication   INT NOT NULL DEFAULT 2024,
  price                 DOUBLE NOT NULL DEFAULT 0,
  genre                 VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE(isbn)
);


CREATE TABLE author (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  name          VARCHAR(50) NOT NULL,
  birthday      DATE NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE book_author (
    book_id     BIGINT,
    author_id   BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE user_detail (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    username    VARCHAR(50) NOT NULL,
	password    VARCHAR(100) NOT NULL,
    role        VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

