CREATE TABLE IF NOT EXISTS "users"(
    id SERIAL PRIMARY KEY,
    -- id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, => difference with serial
    name VARCHAR(30) NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE,
    role TEXT DEFAULT 'USER', -- USER, ADMIN, PROVIDER
    address VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    -- updated_at TIMESTAMP DEFAULT NOW(),
    CHECK (role IN ('USER', 'ADMIN', 'PROVIDER'))
);

CREATE TABLE IF NOT EXISTS "home_services"(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price NUMERIC NOT NULL,
    provider_id INT REFERENCES "users"(id),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS "bookings"(
    id SERIAL PRIMARY KEY,
    service_id INT REFERENCES "home_services"(id),
    user_id INT REFERENCES "users"(id),
    booking_date TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'INITIATED',
    rating INT CHECK (rating BETWEEN 1 AND 5),
    review TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    -- updated_at TIMESTAMP DEFAULT NOW(),
    CHECK(status IN ('INITIATED', 'ACCEPTED', 'REJECTED', 'CANCELLED', 'COMPLETED'))
);

CREATE TABLE IF NOT EXISTS "audit_logs"(
    id SERIAL PRIMARY KEY,
    table_name VARCHAR(20) NOT NULL,
    operation  TEXT,
    done_by INT REFERENCES "users"(id),
    row_id INT NOT NULL,
    changed_at TIMESTAMP DEFAULT NOW(),
    prev_data JSONB,
    new_data JSONB,
    CHECK (operation IN ('INSERT', 'UPDATE', 'DELETE'))
);