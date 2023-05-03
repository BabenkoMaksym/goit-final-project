ALTER TABLE final_project.users ADD CONSTRAINT unique_username_email UNIQUE (username, email);
