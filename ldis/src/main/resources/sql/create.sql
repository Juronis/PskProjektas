create table MEMBER (id int8 not null AUTO_INCREMENT, creditAmount int4 not null, password varchar(2000), EMAIL varchar(150) not null, ISFACEBOOKUSER varchar(2000), isAdmin boolean not null, isFullMember boolean not null, USERNAME varchar(100) not null, primary key (id));
create table Reservation (id int8 not null AUTO_INCREMENT, name varchar(255), member_id int8 not null, summerhouse_id int8 not null, primary key (id));
create table Summerhouse (id int8 not null AUTO_INCREMENT, imageurl varchar(2000), description varchar(2000), numberofplaces int8 not null,  primary key (id));
create table EXTRAACTIVITIES (id int8 not null AUTO_INCREMENT, horses int8, cayaks int8, bicycles int8, childrenactivities int8, trampoline int8, sauna int8,  primary key (id));
alter table MEMBER add constraint UK_heuxbjbqsn0ck6y0ppm6cino4  unique (USERNAME);
alter table MEMBER add constraint UK_heuxbjbqsn0ck6y0ppm6cino4  unique (EMAIL);
alter table Reservation add constraint FK_4er5mu43ud2m4dgcqe4tk4593 foreign key (member_id) references MEMBER(id);
alter table Reservation add constraint FK_skmwi81kweog7rk9g5tpexelq foreign key (summerhouse_id) references Summerhouse(id);