INSERT INTO Nebula.accounts (id, email, password, name) VALUES (1, 'testcase1@live.dk', '$argon2id$v=19$m=32768,t=5,p=1$rqyHSg5luQsKKL01UPCmfg$q++9dwpqivBvaNy7TFkPcq/KIFDcBRNr7+s7Cwk2ECI', 'Test Case 1');
INSERT INTO Nebula.accounts (id, email, password, name) VALUES (2, 'testcase2@live.dk', '$argon2id$v=19$m=32768,t=5,p=1$c0WWya9gtUzhDW2uy4mIVg$RJeabEudSk+WWE+UN5JitBSREdajvtCCIeiwnQ0OPd4', 'Test Case 2');
INSERT INTO Nebula.accounts (id, email, password, name) VALUES (3, 'testcase3@live.dk', '$argon2id$v=19$m=32768,t=5,p=1$wKKjouBF6FQNTzNx40VlWw$bTfIJ+fQN0tThLz3rxz0xFSfXuHWrVZcZNJgRsD4eQc', 'Test Case 3');
INSERT INTO Nebula.accounts (id, email, password, name) VALUES (4, 'testcase4@live.dk', '$argon2id$v=19$m=32768,t=5,p=1$mKTDzd/4Va84s2saakA3gA$asMxlkkYu0rektPAOPoRkRZyKoMIFztvZPyxufxNr6Y', 'Test Case 4');
INSERT INTO Nebula.accounts (id, email, password, name) VALUES (5, 'testcase5@live.dk', '$argon2id$v=19$m=32768,t=5,p=1$KgnsCkRWWkYzeJc6XRr7Dg$eAt+CTeTKQ4SifJ4y0RLWEYxd8yXOGyXKJiQXGQFNK4', 'Test Case 5');

INSERT INTO Nebula.membership_counts (team_id, members, invitations) VALUES (1, 1, 2);

INSERT INTO Nebula.membership_view (account_id, account_name, account_email, team_id, team_name, membership_id, membership_accepted, project_count, membership_count, invitation_count) VALUES (1, 'Test Case 1', 'testcase1@live.dk', 1, 'Team Test', 1, 0x31, 1, 1, 2);
INSERT INTO Nebula.membership_view (account_id, account_name, account_email, team_id, team_name, membership_id, membership_accepted, project_count, membership_count, invitation_count) VALUES (2, 'Test Case 2', 'testcase2@live.dk', 1, 'Team Test', 2, 0x30, 1, 1, 2);
INSERT INTO Nebula.membership_view (account_id, account_name, account_email, team_id, team_name, membership_id, membership_accepted, project_count, membership_count, invitation_count) VALUES (3, 'Test Case 3', 'testcase3@live.dk', 1, 'Team Test', 3, 0x30, 1, 1, 2);

INSERT INTO Nebula.memberships (id, team_id, account_id, accepted) VALUES (1, 1, 1, 0x31);
INSERT INTO Nebula.memberships (id, team_id, account_id, accepted) VALUES (2, 1, 2, 0x30);
INSERT INTO Nebula.memberships (id, team_id, account_id, accepted) VALUES (3, 1, 3, 0x30);

INSERT INTO Nebula.projects (id, team_id, name) VALUES (1, 1, 'Test This Project');

INSERT INTO Nebula.resources (id, project_id, name, color) VALUES (1, 1, 'Testing', '#800000');
INSERT INTO Nebula.resources (id, project_id, name, color) VALUES (2, 1, 'Unit Testing', '#13b4ff');
INSERT INTO Nebula.resources (id, project_id, name, color) VALUES (3, 1, 'Integration Test', '#ab3fdd');

INSERT INTO Nebula.tasks (id, project_id, parent_id, name, startDate, endDate, resource_id, duration, estimated_cost) VALUES (3, 1, null, 'Testing', '2021-12-16 15:26:00', '2021-12-20 15:26:00', 1, null, null);
INSERT INTO Nebula.tasks (id, project_id, parent_id, name, startDate, endDate, resource_id, duration, estimated_cost) VALUES (4, 1, 3, 'Unit Testing', '2021-12-16 15:26:00', '2021-12-17 15:26:00', 2, null, null);
INSERT INTO Nebula.tasks (id, project_id, parent_id, name, startDate, endDate, resource_id, duration, estimated_cost) VALUES (5, 1, 3, 'Integration Test', '2021-12-17 15:26:00', '2021-12-18 15:26:00', 3, null, null);

INSERT INTO Nebula.teams (id, name) VALUES (1, 'Team Test');
