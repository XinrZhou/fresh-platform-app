create database if not exists `user-service`;
use `user-service`;

create table if not exists cart
(
    id          bigint(19)  not null primary key,
    user_id     bigint(19)  not null,
    sku_id      bigint(19)  not null,
    count       tinyint(5)  not null default 1,
    type        tinyint(1)  not null default 0,
    insert_time datetime    not null default current_timestamp,
    update_time datetime    not null default current_timestamp on update current_timestamp,

    unique(user_id, sku_id)
);