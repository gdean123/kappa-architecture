CREATE TABLE tweets (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id),
	text VARCHAR(140) NOT NULL
);