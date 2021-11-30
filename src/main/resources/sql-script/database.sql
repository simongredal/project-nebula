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

create or replace view membership_counts as
select `nebula`.`teams`.`id`                 AS `team_id`,
       (select `counter`.`number`
        from (select count(0) AS `number`
              from (select `nebula`.`teams`.`id` AS `id`, `nebula`.`memberships`.`accepted` AS `membership_accepted`
                    from (`nebula`.`teams`
                             join `nebula`.`memberships`
                                  on ((`nebula`.`memberships`.`team_id` = `nebula`.`teams`.`id`)))) `ic`
              where ((`ic`.`membership_accepted` = true) and (`ic`.`id` = `nebula`.`memberships`.`team_id`))
              group by `ic`.`id`) `counter`) AS `members`,
       (select `counter`.`number`
        from (select count(0) AS `number`
              from (select `nebula`.`teams`.`id` AS `id`, `nebula`.`memberships`.`accepted` AS `membership_accepted`
                    from (`nebula`.`teams`
                             join `nebula`.`memberships`
                                  on ((`nebula`.`memberships`.`team_id` = `nebula`.`teams`.`id`)))) `ic`
              where ((`ic`.`membership_accepted` = false) and (`ic`.`id` = `nebula`.`memberships`.`team_id`))
              group by `ic`.`id`) `counter`) AS `invitations`
from (`nebula`.`teams`
         join `nebula`.`memberships` on ((`nebula`.`memberships`.`team_id` = `nebula`.`teams`.`id`)))
group by `nebula`.`memberships`.`team_id`;

create or replace view membership_view as
select `nebula`.`accounts`.`id`                                                 AS `account_id`,
       `nebula`.`accounts`.`name`                                               AS `account_name`,
       `nebula`.`accounts`.`email`                                              AS `account_email`,
       `nebula`.`teams`.`id`                                                    AS `team_id`,
       `nebula`.`teams`.`name`                                                  AS `team_name`,
       `nebula`.`memberships`.`id`                                              AS `membership_id`,
       `nebula`.`memberships`.`accepted`                                        AS `membership_accepted`,
       (select count(0)
        from `nebula`.`projects`
        where (`nebula`.`projects`.`team_id` = `nebula`.`teams`.`id`))          AS `project_count`,
       (select `nebula`.`membership_counts`.`members`
        from `nebula`.`membership_counts`
        where (`nebula`.`membership_counts`.`team_id` = `nebula`.`teams`.`id`)) AS `membership_count`,
       (select `nebula`.`membership_counts`.`invitations`
        from `nebula`.`membership_counts`
        where (`nebula`.`membership_counts`.`team_id` = `nebula`.`teams`.`id`)) AS `invitation_count`
from ((`nebula`.`teams` join `nebula`.`memberships` on ((`nebula`.`memberships`.`team_id` = `nebula`.`teams`.`id`)))
         join `nebula`.`accounts` on ((`nebula`.`accounts`.`id` = `nebula`.`memberships`.`account_id`)));



