CREATE DATABASE TP2_sysrep_HO;
CREATE DATABASE TP2_sysrep_BO1;
CREATE DATABASE TP2_sysrep_BO2;

USE DATABASE TP2_sysrep_HO;
CREATE TABLE sales (
  id BIGINT NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  region VARCHAR(50) NOT NULL,
  product VARCHAR(50) NOT NULL,
  qty INT NOT NULL,
  cost FLOAT NOT NULL,
  amt FLOAT NOT NULL,
  tax FLOAT NOT NULL,
  total FLOAT NOT NULL,
  PRIMARY KEY (id)
);

USE DATABASE TP2_sysrep_BO1;
CREATE TABLE sales (
  id BIGINT NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  region VARCHAR(50) NOT NULL,
  product VARCHAR(50) NOT NULL,
  qty INT NOT NULL,
  cost FLOAT NOT NULL,
  amt FLOAT NOT NULL,
  tax FLOAT NOT NULL,
  total FLOAT NOT NULL,
  PRIMARY KEY (id)
);

USE DATABASE TP2_sysrep_BO2;
CREATE TABLE sales (
  id BIGINT NOT NULL AUTO_INCREMENT,
  date DATE NOT NULL,
  region VARCHAR(50) NOT NULL,
  product VARCHAR(50) NOT NULL,
  qty INT NOT NULL,
  cost FLOAT NOT NULL,
  amt FLOAT NOT NULL,
  tax FLOAT NOT NULL,
  total FLOAT NOT NULL,
  PRIMARY KEY (id)
);
