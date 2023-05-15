CREATE TABLE IF NOT EXISTS final_project.key_words (
  word VARCHAR(200) PRIMARY KEY NOT NULL CHECK (LENGTH(word) BETWEEN 3 AND 50),
  note_id UUID REFERENCES final_project.notes(id)
);
