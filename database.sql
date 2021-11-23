create table Accounts
(
    ID       int auto_increment,
    name     varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    constraint Account_ID_uindex
        unique (ID),
    constraint Accounts_email_uindex
        unique (email)
);

alter table Accounts
    add primary key (ID);

create table Teams
(
    ID        int auto_increment,
    Project   varchar(255) not null,
    AccountID int          null,
    TeamName  varchar(255) not null,
    constraint Team_ID_uindex
        unique (ID),
    constraint Team_Project_uindex
        unique (Project),
    constraint Teams_TeamName_uindex
        unique (TeamName),
    constraint fk_AccountID
        foreign key (AccountID) references Accounts (ID)
            on delete cascade
);

alter table Teams
    add primary key (ID);

create table Projects
(
    ID          int auto_increment,
    ProjectName varchar(255) not null,
    TeamID      int          null,
    constraint Project_ID_uindex
        unique (ID),
    constraint Project_ProjectName_uindex
        unique (ProjectName),
    constraint fk_TeamID
        foreign key (TeamID) references Teams (ID)
            on delete cascade
);

alter table Projects
    add primary key (ID);

create table Subprojects
(
    ID             int auto_increment,
    Assignment     varchar(255) not null,
    ProjectID      int          null,
    SubprojectName varchar(255) not null,
    constraint Subproject_ID_uindex
        unique (ID),
    constraint Subproject_SubprojectName_uindex
        unique (SubprojectName),
    constraint fk_ProjectID
        foreign key (ProjectID) references Projects (ID)
            on delete cascade
);

alter table Subprojects
    add primary key (ID);

create table Tasks
(
    ID           int auto_increment,
    ProjectID    int          null,
    SubprojectID int          null,
    AccountID    int          null,
    TaskName     varchar(255) not null,
    AccountName  varchar(255) null,
    constraint Tasks_ID_uindex
        unique (ID),
    constraint fkAccountID
        foreign key (AccountID) references Accounts (ID)
            on delete cascade,
    constraint fkProjectID
        foreign key (ProjectID) references Projects (ID)
            on delete cascade,
    constraint fkSubprojectID
        foreign key (SubprojectID) references Subprojects (ID)
            on delete cascade
);

alter table Tasks
    add primary key (ID);

