CREATE TABLE IF NOT EXISTS final_project.message
(
    id                SERIAL PRIMARY KEY,
    chat_id           INTEGER REFERENCES final_project.chat(id),
    user_id           INTEGER REFERENCES final_project.users(id),
    message           VARCHAR(1000) NOT NULL,
    read              BOOLEAN       NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);