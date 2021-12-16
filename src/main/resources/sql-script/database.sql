begin;
set foreign_key_checks = 0;
drop table if exists accounts;
drop table if exists teams;
drop table if exists memberships;
drop table if exists projects;
drop table if exists resources;
drop table if exists tasks;
drop table if exists assignments;
set foreign_key_checks = 1;

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

create table if not exists resources (
    id         int auto_increment primary key,
    project_id    int                   not null,
    name       varchar(255)          not null,
    color      varchar(255)          null,
    constraint resources_projects_fkindex foreign key (project_id) references projects (id) on delete cascade
);
create table if not exists tasks (
    id          int auto_increment primary key,
    project_id  int          not null,
    parent_id   int          null,
    name        varchar(255) null,
    startDate   datetime not null,
    endDate     datetime not null,
    resource_id int          null,
    duration    int          null,
    estimated_cost int       null,
    constraint tasks_projects_fkindex foreign key (project_id) references projects (id)
       on delete cascade,
    constraint tasks_tasks_fkindex foreign key (parent_id) references tasks (id)
       on delete cascade,
    constraint tasks_resources_fkindex foreign key (resource_id) references resources (id) ON DELETE SET NULL
);

create or replace view membership_counts as
select teams.id                        as team_id,
       (select counter.number
        from (select count(0) as number
              from (select teams.id as id, memberships.accepted as membership_accepted
                    from teams
                             join memberships on memberships.team_id = teams.id) as ic
              where ic.membership_accepted = true
                and ic.id = memberships.team_id
              group by ic.id) counter) as members,
       (select counter.number
        from (select count(0) as number
              from (select teams.id as id, memberships.accepted as membership_accepted
                    from teams
                             join memberships on memberships.team_id = teams.id) as ic
              where ic.membership_accepted = false
                and ic.id = memberships.team_id
              group by ic.id) counter) as invitations
from teams
         join memberships on memberships.team_id = teams.id
group by memberships.team_id;

create or replace view membership_view as
select accounts.id                                  as account_id,
       accounts.name                                as account_name,
       accounts.email                               as account_email,
       teams.id                                     as team_id,
       teams.name                                   as team_name,
       memberships.id                               as membership_id,
       memberships.accepted                         as membership_accepted,
       (select count(0)
        from projects
        where projects.team_id = teams.id)          as project_count,
       (select membership_counts.members
        from membership_counts
        where membership_counts.team_id = teams.id) as membership_count,
       (select membership_counts.invitations
        from membership_counts
        where membership_counts.team_id = teams.id) as invitation_count
from teams
         join memberships on memberships.team_id = teams.id
         join accounts on accounts.id = memberships.account_id;



