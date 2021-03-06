DROP TABLE IF EXISTS book;

CREATE TABLE book (
  id     BIGINT(20)     NOT NULL AUTO_INCREMENT,
  name   VARCHAR(255)   NOT NULL,
  author VARCHAR(255)   DEFAULT NULL,
  pages  INT(30)         NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_name (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;




