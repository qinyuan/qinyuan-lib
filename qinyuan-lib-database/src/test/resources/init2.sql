
CREATE TABLE hello_world (
  id int primary key auto_increment,
  first_field char(50),
  second_field char(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into hello_world(id, first_field, second_field) values(1, 'aaa', 'bbb'), (2, 'bbb', 'ccc'), (3, 'eee', 'fff');
