create table if not exists hall
(
  id           serial primary key not null,
  row          smallint,
  place        smallint,
  availability boolean
);

create table if not exists movie_viewers
(
  id       serial primary key not null,
  name     varchar(200),
  phone    varchar(200),
  place_id smallint
);

insert into hall(row, place, availability)
values (1, 1, 'true');
insert into hall(row, place, availability)
values (1, 2, 'true');
insert into hall(row, place, availability)
values (1, 3, 'true');
insert into hall(row, place, availability)
values (2, 1, 'true');
insert into hall(row, place, availability)
values (2, 2, 'true');
insert into hall(row, place, availability)
values (2, 3, 'true');
insert into hall(row, place, availability)
values (3, 1, 'true');
insert into hall(row, place, availability)
values (3, 2, 'true');
insert into hall(row, place, availability)
values (3, 3, 'true');