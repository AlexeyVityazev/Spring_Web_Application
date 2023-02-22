DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO meals (description, calories_per_day)
VALUES ('Обед',450);
