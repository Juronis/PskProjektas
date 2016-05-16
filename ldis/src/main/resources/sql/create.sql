create table MEMBER (id int8 not null, creditAmount int4 not null, EMAIL varchar(150), ISFACEBOOKUSER boolean, isAdmin boolean not null, isFullMember boolean not null, USERNAME varchar(100) not null, primary key (id))
create table Reservation (id int8 not null, name varchar(255), member_id int8 not null, summerhouse_id int8 not null, primary key (id))
create table Summerhouse (id int8 not null, amount numeric(19, 2) not null, primary key (id))
alter table MEMBER add constraint UK_heuxbjbqsn0ck6y0ppm6cino4  unique (USERNAME)
alter table Reservation add constraint FK_4er5mu43ud2m4dgcqe4tk4593 foreign key (member_id) references MEMBER
alter table Reservation add constraint FK_skmwi81kweog7rk9g5tpexelq foreign key (summerhouse_id) references Summerhouse
create sequence hibernate_sequence start 1 increment 1   