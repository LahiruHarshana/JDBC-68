drop database if exists kade;
create database if not exists kade;

use kade;

create table customer(
    id varchar(35) primary key ,
    name varchar(155) not null,
    address text not null,
    tel varchar(15) not null
);

create table orders(
   order_id varchar(35) primary key,
   cus_id varchar(35) not null,
   date date not null,
   constraint foreign key (cus_id) references customer(id)
   on delete cascade on update cascade
);

create table item(
  code varchar(35) primary key ,
  description text not null ,
  unit_price double not null,
  qty_on_hand int not null
);

create table order_detail(
    order_id varchar(35) not null ,
    item_code varchar(35) not null,
    qty int not null,
    unit_price double not null,
    constraint foreign key (order_id) references orders(order_id)
    on delete  cascade on update cascade,
    constraint foreign key (item_code) references item(code)
    on delete cascade on update cascade
);