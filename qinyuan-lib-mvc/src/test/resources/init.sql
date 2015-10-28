
create table user (
  id int primary key auto_increment,
  username char(50),
  password char(50),
  role char(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

insert into user(username, password, role) values('user1', 'password1', 'role1'), ('user3', 'password2', 'role2');
