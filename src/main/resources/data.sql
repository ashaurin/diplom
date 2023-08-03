INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@javaops.ru', '{noop}admin'),
       ('User', 'user2@yandex.ru', '{noop}password');

INSERT INTO USER_ROLE (role, user_id)
VALUES  ('USER', 1),
        ('ADMIN', 2),
        ('USER', 2),
        ('USER', 3);

INSERT INTO restaurant (NAME)
VALUES ('Дядя Ваня'),
       ('Суши-роллы'),
       ('Пануччи');

INSERT INTO dish (DATE, NAME, PRICE, RESTAURANT_ID)
VALUES (CURRENT_DATE, 'Пельмени', 500, 1),
       (CURRENT_DATE, 'Блины', 500, 1),
       (CURRENT_DATE, 'Чай', 100, 1),
       (CURRENT_DATE, 'Филадельфия ролл', 800, 2),
       (CURRENT_DATE, 'Калифорния ролл', 700, 2),
       (CURRENT_DATE, 'Сакэ', 500, 2),
       (CURRENT_DATE, 'Маргарита пицца', 800, 3),
       (CURRENT_DATE, 'Паста Карбонара', 500, 3),
       (CURRENT_DATE, 'Каппучино', 200, 3);

INSERT INTO vote (DATE, RESTAURANT_ID, USER_ID)
VALUES (CURRENT_DATE, 1, 1),
       (CURRENT_DATE, 2, 2);