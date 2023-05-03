CREATE TABLE IF NOT EXISTS final_project.notes (
  id UUID PRIMARY KEY,
  name VARCHAR(200) NOT NULL CHECK (LENGTH(name) BETWEEN 5 AND 100),
  content VARCHAR(20000) NOT NULL CHECK (LENGTH(content) BETWEEN 5 AND 10000),
  access_type VARCHAR(10) NOT NULL CHECK (access_type IN ('private', 'public')),
  user_id INTEGER REFERENCES final_project.users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
