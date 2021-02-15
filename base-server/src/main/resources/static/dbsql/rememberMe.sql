create table `persistent_logins` (
    `username` varchar(64) not null ,
    `series` varchar(64) not null ,
    `token` varchar(64) not null ,
    `last_used` timestamp not null default current_timestamp on update current_timestamp ,
    primary key (`series`)
) ENGINE=InnoDB default charset = utf8;