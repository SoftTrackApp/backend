CREATE TABLE IF NOT EXISTS roles (
ID SERIAL PRIMARY KEY,
Name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
ID VARCHAR(20) PRIMARY KEY,
L_Name VARCHAR(40) NOT NULL,
F_Name VARCHAR(40) NOT NULL,
Role_ID INT NOT NULL DEFAULT 1 REFERENCES roles(ID) ON DELETE SET DEFAULT
);

CREATE TABLE IF NOT EXISTS permissions (
ID SERIAL PRIMARY KEY,
Name VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS role_permission (
Role_ID INT NOT NULL REFERENCES roles(ID) ON DELETE CASCADE,
Permission_ID INT NOT NULL REFERENCES permissions(ID) ON DELETE CASCADE,
PRIMARY KEY (Role_ID, Permission_ID)
);

CREATE TABLE IF NOT EXISTS "groups" (
ID SERIAL PRIMARY KEY,
Name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_group (
User_ID VARCHAR NOT NULL REFERENCES users(ID) ON DELETE CASCADE,
Group_ID INT NOT NULL REFERENCES "groups"(ID) ON DELETE CASCADE,
PRIMARY KEY (User_ID, Group_ID)
);

CREATE TABLE IF NOT EXISTS softskills (
ID SERIAL PRIMARY KEY,
Name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS behaviors (
ID SERIAL PRIMARY KEY,
Name VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS behavior_skill (
Behavior_ID INT NOT NULL REFERENCES behaviors(ID) ON DELETE CASCADE,
Softskill_ID INT NOT NULL REFERENCES softskills(ID) ON DELETE CASCADE,
PRIMARY KEY(Behavior_ID, Softskill_ID)
);

CREATE TABLE IF NOT EXISTS behavior_sets (
ID SERIAL PRIMARY KEY,
Name VARCHAR (40) NOT NULL
);

CREATE TABLE IF NOT EXISTS behavior_to_set (
Behavior_ID INT NOT NULL REFERENCES behaviors(ID) ON DELETE CASCADE,
Set_ID INT NOT NULL REFERENCES behavior_sets(ID) ON DELETE CASCADE,
PRIMARY KEY (Behavior_ID, Set_ID)
);

CREATE TABLE IF NOT EXISTS records (
ID BIGSERIAL PRIMARY KEY,
Title VARCHAR(40) NOT NULL,
Receiver_ID VARCHAR NOT NULL REFERENCES users(ID) ON DELETE CASCADE,
Creator_ID VARCHAR REFERENCES users(ID) ON DELETE SET NULL,
Behavior_ID INT NOT NULL REFERENCES behaviors(ID) ON DELETE CASCADE,
Comment TEXT,
Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO roles(name) VALUES
('студент'),('аудитор'),('методист'),('администратор');

INSERT INTO permissions(name) VALUES
('view_own_dashboard'),
('create_record'),
('view_all_dashboards'),
('manage_competencies'),
('manage_groups'),
('manage_profiles');

INSERT INTO role_permission(role_id,permission_id) VALUES
(4,1),(4,2),(4,3),(4,4),(4,5),(4,6),
(3,1),(3,2),(3,3),(3,4),
(2,1),(2,2),
(1,1);

INSERT INTO softskills(name) VALUES 
('Работа в команде'),('Вербальная коммуникация');

INSERT INTO behaviors(name) VALUES 
('Предлагает новые идеи'),
('Помогает сокомандникам'),
('Выводит на дискуссию'),
('Положительно воспринимает критику'),
('Дает обратную связь'),
('Адаптирует стиль общения под аудиторию'),
('Структурирует мысли'),
('Уточняет требования к задаче'),
('Предлагает альтернативные решения'),
('Задает вопросы'),
('Активно слушает'),
('Возвращается к теме дискуссии'),
('Анализирует информацию');

INSERT INTO behavior_sets(name) VALUES 
('Проектная деятельность "Решение кейсов"'),
('Философия/Литература'),
('Инженерное мышление');

INSERT INTO behavior_skill(behavior_id,softskill_id) VALUES
(1, 1),(2, 1),(3, 1),(4, 1),(5, 1),(6, 1),(7, 1),(8, 1),(9, 1),
(7, 2),(10, 2),(6, 2),(11, 2),(5, 2),(4, 2),(8, 2),(12, 2),(13, 2);

INSERT INTO behavior_to_set (behavior_id, set_id) VALUES
(1, 1),(8, 1),(6, 1),(7, 1),(10, 1),(12, 1),(13, 1),(9, 1),
(3, 2),(4, 2),(5, 2),(7, 2),(6, 2),(11, 2),(12, 2),
(1, 3),(2, 3),(8, 3),(9, 3),(13, 3);

INSERT INTO groups(name) VALUES ('test');

INSERT INTO users(id,l_name,f_name,role_id) VALUES 
('student','Студентович','Студент',1),
('auditor','Аудиторов','Аудитор',2),
('methodist','Методистов','Методист',3),
('admin','Админыч','Админ',4);

INSERT INTO user_group(user_id,group_id) VALUES
('student',1),('auditor',1),('methodist',1),('admin',1);