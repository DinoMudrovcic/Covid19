INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users(username, password) VALUES ('admin', '$2a$10$m4EAcyPGH1iJfEPUOKNKLudIa0ISj0Jl4Ewn6PyKxHeUEIQAk3dr6');
INSERT INTO users(username, password) VALUES ('user', '$2a$10$hYd7AjYAXs/l7s.W.wcm8.vSh6vNmO5Ngcqp1MP0nvsmRW68e2..y');

INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));
INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));
INSERT INTO user_roles(user_id, role_id) VALUES ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));