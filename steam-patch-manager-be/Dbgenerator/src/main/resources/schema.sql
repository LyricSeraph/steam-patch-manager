create extension if not exists "pgcrypto";
create extension if not exists "uuid-ossp";

create schema if not exists project;

create table if not exists project.patch
(
    id            serial                                                     constraint patch_pk primary key,
    app_id        bigint                                            not null,
    patch_name    varchar(256) default 'default'::character varying not null,
    app_version   varchar(256),
    patch_version varchar(256),
    reference     varchar(1024),
    description   text,
    created_at    timestamp default now()                           not null,
    updated_at    timestamp default now()                           not null
);

create table if not exists project.storage
(
    id           uuid                    not null constraint storage_pk primary key,
    storage_type varchar(20)             not null,
    patch_id     bigint                  not null,
    filename     varchar(256)            not null,
    description  text,
    size         bigint                  not null,
    md5          varchar(40),
    created_at   timestamp default now() not null,
    updated_at   timestamp default now() not null
);

/* Triggers */
create or replace function trigger_set_timestamp()
    returns trigger as '
    begin
        new.updated_at = now();
        return new;
    end;
' language plpgsql;

create or replace procedure create_update_triggers()
    language plpgsql as '
    declare
        tn varchar;
    begin
        for tn in
            select table_name from information_schema.tables where table_schema = ''project''
            loop
                execute ''create or replace trigger set_timestamp before update on project.'' ||
                        tn || '' for each row execute procedure trigger_set_timestamp()'';
            end loop;
    end
';

call create_update_triggers()