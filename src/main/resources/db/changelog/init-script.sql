create table category(
    id        bigserial primary key,
    name      text   not null,
    parent_id bigint references category(id),
    user_id   bigint not null
);