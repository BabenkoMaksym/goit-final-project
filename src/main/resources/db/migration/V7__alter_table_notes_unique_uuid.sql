ALTER TABLE notes
ALTER COLUMN id SET DEFAULT uuid_generate_v4();