create table Client(
                       clientId serial primary key,
                       name varchar(20) not null,
                       address varchar(100) not null,
                       email varchar(50) unique not null,
                       phone varchar(15) unique not null,
                       noIdent varchar(20) unique not null,
                       nationality varchar(15) not null,
                       atrDisc varchar(2) not null,
                       constraint check_email_format check(email like '%@%'),
                       constraint check_atrDisc check(atrDisc in ('C', 'G'))
);

create table Store(
                      code serial primary key,
                      manager int unique not null references client(clientId) ,
                      address varchar(100) not null,
                      phone varchar(9) unique not null,
                      email varchar(50) unique not null,
                      constraint check_email_format check(email like '%@%')
);

create table device(
                       deviceNumber serial primary key,
                       latitude numeric(6,4) not null,
                       longitude numeric(6,4) not null,
                       battery int not null,
                       constraint check_battery check (battery between 0 and 100)
);

create table bike(
                     bikeId serial primary key,
                     weight numeric(4,2) not null,
                     model varchar(20) not null,
                     brand varchar(20) not null,
                     state varchar(20) not null,
                     atrDisc varchar(2) not null,
                     shift int not null,
                     device int unique not null references device(deviceNumber),
                     constraint check_state check(state in ('free', 'occupy', 'reserved', 'under maintenance')),
                     constraint check_bike_type check(atrDisc in ('E', 'C')),
                     constraint check_shifts check(shift in (1, 6, 18, 24))
);

create table electricBike(
                             bike int unique not null references bike(bikeId),
                             autonomy int not null,
                             velocity int not null
);

create table reservation(
                        noReservation serial not null,
                        store int not null references store(code),
                        primary key(noReservation, store),
                        startDate timestamp not null,
                        endDate timestamp,
                        bike int not null references bike(bikeId),
                        client int not null references Client(clientId),
                        value numeric(4,2)
);


insert into client (name, address , email , phone, noIdent, nationality, atrdisc) values ('alice', 'chelas', 'alice@hotmail.com', '123456789', '234253255','Portuguese' ,'C');
insert into client (name, address , email , phone, noIdent, nationality, atrdisc) values ('desmond', 'porto', 'desmond@hotmail.com', '3270523750', '252379032', 'English', 'G');
insert into client (name, address , email , phone, noIdent, nationality, atrdisc) values ('bob', 'sintra', 'bob@hotmail.com', '987654321', '983329805', 'Chinese', 'C');
insert into client (name, address , email , phone, noIdent, nationality, atrdisc) values ('richard', 'olivais', 'richard@hotmail.com', '358253523', '235235235', 'Portuguese', 'C');

insert into store(manager, address, phone, email) values ((select clientId from client where name = 'desmond'), 'porto', '213455678', 'store1@hotmail.com');

insert into device(latitude, longitude, battery) values (45.14, 60.00, 100);
insert into device(latitude, longitude, battery) values (95.89, 80.00, 100);
insert into device(latitude, longitude, battery) values (75.09, 12.00, 100);
insert into device(latitude, longitude, battery) values (69.80, 20.00, 100);


insert into bike(weight, model, brand, state, atrdisc, shift, device) values(45.00, 'model1', 'brand1', 'free', 'C', 24, 1);
insert into bike(weight, model, brand, state, atrdisc, shift, device) values(55.00, 'model2', 'brand2', 'occupy', 'E', 18, 2);
insert into bike(weight, model, brand, state, atrdisc, shift, device) values(40.00, 'model3', 'brand3', 'reserved', 'C', 18,3);
insert into bike(weight, model, brand, state, atrdisc, shift, device) values(45.00, 'model4', 'brand4', 'under maintenance','E' ,6, 4);

insert into electricBike values (2, 500, 33);
insert into electricBike values (4, 450, 30);

insert into reservation (store, startDate, endDate, value,bike, client) values (1, now(), null, null,2 ,1);
insert into reservation (store, startDate, endDate, value,bike, client) values(1, now(), null, null,3,3);