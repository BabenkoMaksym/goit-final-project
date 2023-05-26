CREATE TABLE IF NOT EXISTS final_project.chat_users
(
        chat_id           INTEGER REFERENCES final_project.chat (id),
        username          VARCHAR(50) REFERENCES final_project.users (username)
);