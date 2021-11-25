begin;
set foreign_key_checks = 0;
drop table if exists accounts;
drop table if exists teams;
drop table if exists memberships;
drop table if exists projects;
drop table if exists tasks;
set foreign_key_checks = 1;

create table accounts
(
    id       int auto_increment primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) not null,
    constraint accounts_email_uindex unique (email)
);

create table teams
(
    id   int auto_increment primary key,
    name varchar(255) not null
);

create table memberships
(
    id         int auto_increment primary key,
    team_id    int       not null,
    account_id int       not null,
    accepted   binary(1) null,
    constraint memberships_team_account_uindex unique (team_id, account_id),
    constraint memberships_accounts_fkindex foreign key (account_id) references accounts (id),
    constraint memberships_teams_fkindex foreign key (team_id) references teams (id) on delete cascade
);

create table projects
(
    id      int auto_increment primary key,
    team_id int          not null,
    name    varchar(255) not null,
    constraint projects_teams_fk foreign key (team_id) references teams (id) on delete cascade
);

create table tasks
(
    id         int auto_increment primary key,
    project_id int          not null,
    parent_id  int          null,
    name       varchar(255) null,
    constraint tasks_projects_fk foreign key (project_id) references projects (id) on delete cascade,
    constraint tasks_tasks_fk foreign key (parent_id) references tasks (id) on delete cascade
);