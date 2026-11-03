CREATE TABLE authorities (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(150) NOT NULL UNIQUE,
    status VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE user_authorities (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    authority_id BIGINT NOT NULL REFERENCES authorities(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, authority_id)
);

CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    license_number VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(50)
);

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    make VARCHAR(100),
    model VARCHAR(100),
    manufacture_year INTEGER,
    license_plate VARCHAR(50) NOT NULL UNIQUE,
    car_status VARCHAR(50),
    driver_id BIGINT REFERENCES drivers(id) ON DELETE SET NULL
);

CREATE TABLE maintenances (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id) ON DELETE CASCADE,
    description TEXT,
    cost DOUBLE PRECISION,
    service_date DATE,
    next_maintenance_date DATE
);

