CREATE TABLE IF NOT EXISTS final_project.message
(
    id                SERIAL PRIMARY KEY,
    sender_user_id    INTEGER REFERENCES final_project.users (id),
    recipient_user_id INTEGER REFERENCES final_project.users (id),
    message           VARCHAR(1000) NOT NULL,
    read               BOOLEAN       NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);