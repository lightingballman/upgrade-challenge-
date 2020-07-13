---------------camp_site table------------------------------
DROP TABLE IF EXISTS camp_site;

CREATE TABLE CAMP_SITE (
  ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  SITE_NUMBER BIGINT NOT NULL,
  CAPACITY INT NOT NULL
);

INSERT INTO CAMP_SITE (SITE_NUMBER, CAPACITY ) values(1,10);

---------------user table-----------------------------------
drop table if exists user;

create table user(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) not null,
    last_name  VARCHAR(50) not null,
    email  VARCHAR(50) not null
);
ALTER TABLE user ADD CONSTRAINT email_unique UNIQUE (email);

---------------reservation table----------------------------
drop table if exists reservation;

create table reservation(
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_day DATE not null,
    resevered_by BIGINT not null,
    start_day DATE not null,
    end_day DATE not null,
    reservation_status varchar(10),
    foreign key (resevered_by) references user(user_id)
);

---------------camp_schedule table----------------------------
drop table if exists camp_schedule;

create table camp_schedule(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    calendar_day DATE not null,
    reservation_id BIGINT,
    is_available int not null,
    version BIGINT not null,
    foreign key (reservation_id) references reservation(reservation_id)
);
CREATE INDEX camp_schedule_index_cd ON camp_schedule(calendar_day);


---------------reservation_history table------------------------
drop table if exists reservation_history;

create table reservation_history(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT not null,
    reservation_id BIGINT not null,
    action varchar(20) ,
    description varchar(100),
    action_day DATE not null,
    foreign key (user_id) references user(user_id),
    foreign key (reservation_id) references reservation(reservation_id)
);

CREATE INDEX reservation_history_usr ON reservation_history(user_id);
CREATE INDEX reservation_history_rs ON reservation_history(reservation_id);