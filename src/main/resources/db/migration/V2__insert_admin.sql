INSERT INTO authorities (type) VALUES ('ROLE_ADMIN'), ('ROLE_FLEET_MANAGER') ON CONFLICT DO NOTHING;

INSERT INTO users (name, email, status, password, created_at, updated_at)
VALUES ('Admin', 'admin@gmail.com', 'ACTIVE', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', NOW(), NOW())
ON CONFLICT (email) DO UPDATE SET password = EXCLUDED.password;

INSERT INTO user_authorities (user_id, authority_id)
VALUES (1, 1)
ON CONFLICT DO NOTHING;
