CREATE TABLE IF NOT EXISTS final_project.users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE CHECK (username ~ '^[A-Za-z0-9]{5,50}$'),
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL
);
