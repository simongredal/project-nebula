create table if not exists accounts (
    id       int auto_increment primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) not null,
    constraint accounts_email_uindex unique (email)
);

create table if not exists teams (
    id   int auto_increment primary key,
    name varchar(255) not null
);

create table if not exists memberships (
    id         int auto_increment primary key,
    team_id    int       not null,
    account_id int       not null,
    accepted   binary(1) null,
    constraint memberships_team_id_account_id_uindex unique (team_id, account_id),
    constraint memberships_accounts_fkindex foreign key (account_id) references accounts (id),
    constraint memberships_teams_fkindex foreign key (team_id) references teams (id) on delete cascade
);

create table if not exists projects (
    id      int auto_increment primary key,
    team_id int          not null,
    name    varchar(255) not null,
    constraint projects_teams_fkindex foreign key (team_id) references teams (id) on delete cascade
);

create table if not exists tasks (
    id         int auto_increment primary key,
    project_id int          not null,
    parent_id  int          null,
    name       varchar(255) null,
    constraint tasks_projects_fkindex foreign key (project_id) references projects (id) on delete cascade,
    constraint tasks_tasks_fkindex foreign key (parent_id) references tasks (id) on delete cascade
);

create or replace view membership_view as
select accounts.id          as account_id,
       accounts.name        as account_name,
       accounts.email       as account_email,
       teams.id             as team_id,
       teams.name           as team_name,
       memberships.accepted as membership_accepted,
       memberships.id       as membership_id,
       (select ic.count
       from (select teams.id as id, count(0) as count
              from teams
                  join memberships on memberships.team_id = teams.id
              where memberships.accepted = true
              group by teams.id) ic
        where ic.id = memberships.team_id) as membership_count
from teams
    join memberships on memberships.team_id = teams.id
    join accounts on accounts.id = memberships.account_id;

