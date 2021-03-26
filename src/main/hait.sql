drop table t_admin;
drop table t_city;
drop table t_application;
drop table t_owner;
drop table t_rstrnt;
drop table t_reserv;

select * from t_admin;
select * from t_city;
select * from t_application;
select * from t_owner;
select * from t_rstrnt;
select * from t_reserv;

CREATE TABLE t_admin (
	id varchar(5) not null unique,
	pw varchar(100) not null
);

CREATE TABLE t_city (
  pk int unsigned NOT NULL,
  nm varchar(20) NOT NULL,
  PRIMARY KEY (pk),
  UNIQUE KEY (nm)
);

CREATE TABLE t_application (
	pk int unsigned auto_increment primary key,
	owner_nm varchar(50) not null,
	owner_email varchar(100) not null unique,
	owner_contact varchar(20) not null unique,
	rstrnt_nm varchar(50) not null unique,
	city_pk int unsigned not null,
	more_info varchar(2000),
	process_status int default 0,
	regdate datetime default now(),
	FOREIGN KEY (city_pk) REFERENCES t_city(pk),
	CONSTRAINT CHECK(process_status IN (-1, 0, 1))
);

CREATE TABLE t_owner (
	pk int unsigned auto_increment primary key,
	nm varchar(50) not null,
	pw varchar(100) not null,
	email varchar(100) not null unique,
	contact varchar(20) not null unique,
	regdate datetime default now()
);

CREATE TABLE t_rstrnt (
	pk int unsigned auto_increment primary key,
	owner_pk int unsigned,
	nm varchar(50) not null,
	contact varchar(20) unique,
	location varchar(100) unique,
	city_pk int unsigned not null,
	more_info varchar(2000),
	profile_img varchar(200) unique,
	state int default -1,
	enabled boolean default false,
	regdate datetime default now(),
	FOREIGN KEY (owner_pk) REFERENCES t_owner(pk),
	FOREIGN KEY (city_pk) REFERENCES t_city(pk),
	constraint check(state in(-1, 0, 1))
);

CREATE TABLE t_reserv (
	pk int unsigned auto_increment primary key,
	seq int unsigned not null,
	rstrnt_pk int unsigned,
	contact varchar(20) not null,
	headcount int unsigned not null,
	process_status int default 0,
	regdate datetime default now(),
	last_update datetime,
	FOREIGN KEY (rstrnt_pk) REFERENCES t_rstrnt(pk),
	constraint check (process_status IN (-3, -2, -1, 0, 1, 2))
);

INSERT INTO t_admin (id, pw)
VALUES ('admin', '$2a$10$zquxywUxGO2F2/VZZ23QCObqQ2CxcKsfc.rilJ.SkaXuM7HTJegvS');

INSERT INTO t_city 
(pk, nm)
VALUES 
(10,'강원도'),
(9,'경기도'),
(14,'경상남도'),
(13,'경상북도'),
(5,'광주광역시'),
(4,'대구광역시'),
(6,'대전광역시'),
(2,'부산광역시'),
(1,'서울특별시'),
(8,'세종특별자치시'),
(7,'울산광역시'),
(3,'인천광역시'),
(16,'전라남도'),
(15,'전라북도'),
(17,'제주특별자치도'),
(12,'충청남도'),
(11,'충청북도');
