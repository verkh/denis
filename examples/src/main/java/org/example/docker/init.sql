CREATE TABLE IF NOT EXISTS my_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO my_table (name) VALUES ('John'), ('Alice'), ('Bob');