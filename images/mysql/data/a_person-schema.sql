
GRANT ALL ON *.* TO 'amtUser'@'%' IDENTIFIED BY 'admin' WITH GRANT OPTION;


DROP SCHEMA IF EXISTS person;
CREATE SCHEMA person;
USE person;
--
-- Table structure for table `Person`
--
CREATE TABLE personData (
  person_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  gender VARCHAR(45) NOT NULL,
  birthday VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  phone VARCHAR(45) NOT NULL,
  primary key(person_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

