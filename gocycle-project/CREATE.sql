drop table if exists public.Client CASCADE;
drop table if exists public.Store CASCADE;
drop table if exists public.Device CASCADE;
drop table if exists public.Reservation CASCADE;
drop table if exists public.ElectricBike CASCADE;

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
                            version int not null
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

insert into reservation (store, startDate, endDate, value,bike, client,version) values (1, now(), null, null,2 ,1,1);
insert into reservation (store, startDate, endDate, value,bike, client, version) values(1, now(), null, null,3,3,1);

CREATE OR REPLACE FUNCTION check_reservation_integrity(
    IN customer_id INTEGER,
    IN start_date TIMESTAMP,
    IN bike_id INTEGER,
    OUT is_doable BOOLEAN
)
AS $$
DECLARE
    total_bikes INT;
    reserved_bikes INT;
BEGIN
    -- Check if the client has a reservation at this start date
    IF EXISTS (
        SELECT 1 FROM reservation r WHERE r.client = customer_id AND r.startDate = start_date
    ) THEN
        is_doable := false;
        RETURN;
    END IF;

    -- Check if the requested bike is reserved for this time
    IF EXISTS (
        SELECT 1 FROM reservation r
        WHERE r.bike = bike_id
          AND (
            (start_date BETWEEN r.startDate AND r.endDate)
                OR (r.endDate IS NULL AND start_date >= r.startDate)
            )
    ) THEN
        is_doable := false;
        RETURN;
    END IF;

    -- Check if there are less than 10% available bikes
    SELECT COUNT(*) INTO total_bikes FROM bike;
    SELECT COUNT(*) INTO reserved_bikes FROM reservation r WHERE start_date BETWEEN r.startDate AND r.endDate;

    IF total_bikes - reserved_bikes < (total_bikes * 0.1) THEN
        is_doable := false;
    ELSE
        is_doable := true;
    END IF;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION checkAvailability(bikeId INTEGER, checkTime TIMESTAMP)
    RETURNS Boolean AS $$
    DECLARE
       r record;
BEGIN
raise notice 'before for';
for r in SELECT *
         FROM Reservation rer
                  JOIN Bike b ON rer.bike = b.bikeId loop

    	IF r.State = 'free' THEN
            RETURN TRUE;
        ELSIF checkTime <r.startDate or checkTime > r.endDate THEN
            RETURN True;

        ELSIF RETURN False;
        END IF;
end loop;
    raise notice 'HERE';
    Return false --not sure if prevously return true before leave gets here and return false
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_insertBike() RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT atrDisc FROM bike WHERE bikeId = NEW.bike) != 'E' THEN
        RAISE EXCEPTION 'The bike with id % is not an electric bike', NEW.bike;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_if_bikeEletric
    BEFORE INSERT ON electricBike
    FOR EACH ROW
    EXECUTE FUNCTION check_insertBike();


CREATE OR REPLACE FUNCTION checkStartDate() RETURNS TRIGGER AS $$
	declare
r record;
BEGIN

for r in select startDate from reservation loop
    if  r.startDate=new.startDate then
	raise exception 'the bike is already reserved for that date';
end if;
end loop;
return new;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_start_date
    BEFORE INSERT ON reservation
    FOR EACH ROW
    EXECUTE FUNCTION checkStartDate();

       --test before the version
--insert into reservation (store, startDate, endDate, value,bike, client) values (1,'2024-05-30 23:49:39.45903', null, null,4 ,1);
