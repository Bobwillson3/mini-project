create database if not exists session_service;
use session_service;

create table session (
    id varchar(50) primary key,
    user_id varchar(50) not null,
    session_status varchar(50) not null,
    start_date date not null,
    start_time datetime not null,
    end_time datetime,
    restaurant_id varchar(50)
);

create table user(
	id varchar(50) primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    user_name varchar(255) not null,
    user_password varchar(255) not null
);


create table restaurant(
	id bigint primary key,
    restaurant_name varchar(255) not null
);


create table session_restaurant(
	session_id varchar(50),
    user_id varchar(50),
    restaurant_id varchar(50),
    primary key (session_id, user_id, restaurant_id)
);
create unique index user_name_index on user(user_name);
create unique index start_date_index on session(start_date);
create unique index user_index on session_restaurant (user_id);
create unique index session_index on session_restaurant (session_id);
create unique index restaurant_name_index on restaurant (restaurant_name);

create user 'springuser'@'%' identified by 'ThePassword';
grant all on session_service.* to 'springuser'@'%';