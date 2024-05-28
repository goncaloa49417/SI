create table Cliente(
	id serial primary key,
	name varchar(20) not null,
	address varchar(100) not null,
	email varchar(50) unique not null,
	phone varchar(15) unique not null,
	CC varchar(20) unique not null,
	nationality varchar(15) not null,
	atrdisc varchar(2) not null,
	constraint check_email_format check(email like '%@%'),
	constraint check_atrdisc check(atrdisc in ('C', 'G'))
);

create table Store(
	code serial primary key,
	manager int unique not null references cliente(id) ,
	address varchar(100) not null,
	phone varchar(9) unique not null,
	email varchar(50) unique not null,
	constraint check_email_format check(email like '%@%')
);

create table GPS(
	id serial primary key,
	latitude numeric(6,4) not null,
	longitude numeric(6,4) not null,
	battery int not null,
	constraint check_battery check (battery between 0 and 100)
);

create table bike(
	id serial primary key,
	weight numeric(4,2) not null,
	model varchar(20) not null, 
	brand varchar(20) not null,
	state varchar(20) not null,
	atrdisc varchar(2) not null,
	shifts int not null,
	device int unique not null references GPS(id),
	constraint check_state check(state in ('free', 'occupy', 'reserved', 'under maintenance')),
	constraint check_byke_type check(atrdisc in ('E', 'C')),
	constraint check_shifts check(shifts in (1, 6, 18, 24))
);

create table eletric_bike(
	id int unique not null references bike(id),
	autonomy int not null,
	velocity int not null
);

create table reservation(
	no_reserve serial not null,
	store int not null references store(code),
	primary key(no_reserve, store),
	start_date timestamp not null,
	end_date timestamp,
	value numeric(4,2)
);


insert into cliente (name, address , email , phone, CC, nationality, atrdisc) values ('alice', 'chelas', 'alice@hotmail.com', '123456789', '234253255','Portuguese' ,'C');
insert into cliente (name, address , email , phone, CC, nationality, atrdisc) values ('bob', 'sintra', 'bob@hotmail.com', '987654321', '983329805', 'Chinese', 'C');
insert into cliente (name, address , email , phone, CC, nationality, atrdisc) values ('desmond', 'porto', 'desmond@hotmail.com', '3270523750', '252379032', 'English', 'G');
insert into cliente (name, address , email , phone, CC, nationality, atrdisc) values ('richard', 'olivais', 'richard@hotmail.com', '358253523', '235235235', 'Portuguese', 'C');

insert into store(manager, address, phone, email) values (7, 'porto', '213455678', 'store1@hotmail.com');

insert into gps(latitude, longitude, battery) values (45.14, 60.00, 100);
insert into gps(latitude, longitude, battery) values (95.89, 80.00, 100);
insert into gps(latitude, longitude, battery) values (75.09, 12.00, 100);
insert into gps(latitude, longitude, battery) values (69.80, 20.00, 100);


insert into bike(weight, model, brand, state, atrdisc, shifts, device) values(45.00, 'model1', 'brand1', 'free', 'C', 24, 1);
insert into bike(weight, model, brand, state, atrdisc, shifts, device) values(55.00, 'model2', 'brand2', 'occupy', 'E', 18, 3);
insert into bike(weight, model, brand, state, atrdisc, shifts, device) values(40.00, 'model3', 'brand3', 'reserved', 'C', 18,4);
insert into bike(weight, model, brand, state, atrdisc, shifts, device) values(45.00, 'model4', 'brand4', 'under maintenance','E' ,6, 5);

insert into eletric_bike values (2, 500, 33);
insert into eletric_bike values (4, 450, 30);

insert into reservation (store, start_date, end_date, value) values (1, now(), null, null);
insert into reservation (store, start_date, end_date, value) values(1, now(), null, null);