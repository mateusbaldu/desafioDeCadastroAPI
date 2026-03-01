CREATE TYPE pet_type AS ENUM ('cat', 'dog');
CREATE TYPE pet_sex AS ENUM ('male', 'female');

CREATE TABLE address (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    street VARCHAR(50),
    number INT,
    complement VARCHAR(30),
    district VARCHAR(50),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE pet (
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(20),
    type pet_type NOT NULL,
    sex pet_sex NOT NULL,
    age DECIMAL(2,1),
    weight DECIMAL(2,2),
    race VARCHAR(20),
    address_id INTEGER UNIQUE,
    CONSTRAINT pk_pet PRIMARY KEY (id),
    CONSTRAINT fk_pet_address FOREIGN KEY (address_id) REFERENCES address(id)
);