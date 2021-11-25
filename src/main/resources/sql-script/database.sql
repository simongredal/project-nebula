BEGIN;
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS tasks;
SET foreign_key_checks = 1;

CREATE TABLE accounts
(
    id       int AUTO_INCREMENT PRIMARY KEY,
    email    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    name     varchar(255) NOT NULL,
    CONSTRAINT accounts_email_uindex UNIQUE (email)
);
CREATE TABLE teams
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL
);
CREATE TABLE memberships
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    team_id    int    NOT NULL,
    account_id int    NOT NULL,
    accepted   binary NULL,
    CONSTRAINT memberships_teams_fkindex FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT memberships_accounts_fkindex FOREIGN KEY (account_id) REFERENCES accounts (id)
);
CREATE TABLE projects
(
    id      int AUTO_INCREMENT PRIMARY KEY,
    team_id int          NOT NULL,
    name    varchar(255) NOT NULL,
    CONSTRAINT projects_teams_fkindex FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE
);
CREATE TABLE tasks
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    project_id int          NOT NULL,
    parent_id  int          NULL,
    name       varchar(255) NULL,
    CONSTRAINT tasks_projects_fkindex FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
    CONSTRAINT tasks_tasks_fkindex FOREIGN KEY (parent_id) REFERENCES tasks (id) ON DELETE CASCADE
);
COMMIT;