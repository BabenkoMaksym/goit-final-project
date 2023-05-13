CREATE TABLE IF NOT EXISTS final_project.comments (
                          id UUID PRIMARY KEY,
                          note_id UUID NOT NULL REFERENCES final_project.notes(id),
                          user_id INT NOT NULL REFERENCES final_project.users(id),
                          comment_text VARCHAR(10000) NOT NULL,
                          created_at TIMESTAMP NOT NULL
);