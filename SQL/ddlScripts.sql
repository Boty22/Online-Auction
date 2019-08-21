DROP DATABASE IF EXISTS auctionDB;
CREATE DATABASE auctionDB;
USE auctionDB;

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts(
  ID INT unsigned not null,
  FirstName VARCHAR(50) not null,
  LastName VARCHAR(50) not null,
  StreetAddress VARCHAR(50) not null,
  City VARCHAR(50) not null,
  State VARCHAR(50) not null,
  Country VARCHAR(50) not null,
  ZipCode VARCHAR(50) not null,
  Email VARCHAR(50) not null,
  constraint pk_account PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS items;

CREATE TABLE items
(
  ID INT unsigned not null,
  Name VARCHAR(50) not null,
  Description VARCHAR(200) not null,
  StartingPrice DECIMAL(13,2) not null,
  Availability BOOLEAN not null,
  Image TINYBLOB,
  OwnerAccountID INT unsigned not null,
  PriceSold DECIMAL(13,2),
  CONSTRAINT pk_item PRIMARY KEY (ID),
  CONSTRAINT fk_item_account FOREIGN KEY (ID) REFERENCES accounts(ID)
);

DROP TABLE IF EXISTS bidding_schedules;

CREATE TABLE bidding_schedules(
  ID INT unsigned not null,
  ItemID INT unsigned not null,
  OwnerAccountID INT unsigned not null,
  BiddingDate DATETIME not null,
  BiddingDuration TIMESTAMP,
  CONSTRAINT pk_bidding_schedule PRIMARY KEY (ID),
  CONSTRAINT fk_bid_schd_item FOREIGN KEY (ItemID) REFERENCES items(ID),
  CONSTRAINT fk_bid_schd_account FOREIGN KEY (OwnerAccountID) REFERENCES accounts(ID)
);

DROP TABLE IF EXISTS REPUTATION;

CREATE TABLE REPUTATION(
  AccountID INT unsigned not null,
  UpVotes INT unsigned,
  DownVotes INT unsigned,
  CONSTRAINT pk_reputation PRIMARY KEY (AccountID),
  CONSTRAINT fk_reputation_account FOREIGN KEY (AccountID) REFERENCES accounts(ID)
);
