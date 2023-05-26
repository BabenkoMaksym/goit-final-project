CREATE TABLE IF NOT EXISTS final_project.chat
(
        id                SERIAL PRIMARY KEY,
        chat_type         VARCHAR(16) NOT NULL,
        chat_name         VARCHAR(32)
);