DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE meals
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    datetime         TIMESTAMP DEFAULT now() NOT NULL,
    description      VARCHAR   NOT NULL,
    calories_per_day INTEGER   NOT NULL,
    FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_datetime_idx ON meals (datetime);