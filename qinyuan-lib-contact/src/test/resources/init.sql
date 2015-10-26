
create table mail_account (
  id int primary key auto_increment,
  reference_id int not null,
  type char(50) not null,
  unique (reference_id, type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table simple_mail_account (
  id int primary key auto_increment,
  host char(200) not null,
  username char(100) unique not null,
  password char(100) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create table sendcloud_account (
  id int primary key auto_increment,
  user char(200) not null,
  api_key char(200) not null,
  domain_name char(200) not null,
  unique(user, domain_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/************** data ************************/
insert into mail_account(reference_id, type) values(2, "SimpleMailAccount"), (1, "SimpleMailAccount"), (1, "SendCloudAccount");

insert into simple_mail_account(host,username,password) values('host1', 'username1', 'password1'), ('host2', 'username2', 'password2');

insert into sendcloud_account(user, api_key, domain_name) values('user1', 'apiKey1', 'domainName1');
