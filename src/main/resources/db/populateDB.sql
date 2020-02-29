DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-02-28 08:00', 'завтрак', 500),
       (100000, '2020-02-28 14:00', 'обед', 1000),
       (100000, '2020-02-28 20:00', 'ужин', 500),
       (100000, '2020-02-29 20:00', 'ужин', 500),
       (100000, '2020-02-29 21:00', 'ужин 2', 500),
       (100001, '2020-02-29 11:00', 'ланч', 400),
       (100001, '2020-02-29 16:00', 'полдник', 500);


