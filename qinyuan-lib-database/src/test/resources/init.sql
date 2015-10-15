
CREATE TABLE test_table1(
  id int primary key auto_increment,
  first_field char(50),
  second_field char(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into test_table1(id, first_field, second_field) values(1, 'aaa', 'bbb'), (2, 'bbb', 'ccc'), (3, '张三', '李四');

create table user (
  id int primary key auto_increment,
  username char(50),
  password char(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into user(username, password) values('user1', 'password1'), ('user2', 'password2'), ('user3', 'password3'),
  ('user4', 'password3');

create table ranking (
 id int primary key auto_increment,
 ranking int
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into ranking values(1, 4), (2, 3), (3, 2), (4, 1);
