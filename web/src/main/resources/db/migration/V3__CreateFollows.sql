CREATE TABLE follows (
	id SERIAL PRIMARY KEY,
	follower_id INTEGER NOT NULL REFERENCES users(id),
	followed_id INTEGER NOT NULL REFERENCES users(id)
);