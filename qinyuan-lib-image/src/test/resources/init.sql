
create table image_map (
  id int primary key auto_increment,
  relate_type char(50) not null,
  relate_id int not null,
  x_start int not null,
  y_start int not null,
  x_end int not null,
  y_end int not null,
  href varchar(800) not null,
  comment char(200) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into image_map values(1, 'testType', 8, 9, 10, 11, 12, 'href1', 'comment1')