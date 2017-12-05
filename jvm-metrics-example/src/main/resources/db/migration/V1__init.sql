DROP TABLE IF EXISTS book;

CREATE TABLE book (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  author varchar(255) DEFAULT NULL,
  pages int(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




