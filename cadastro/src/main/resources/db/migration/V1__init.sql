CREATE type pet_type AS ENUM ('cat', 'dog');
CREATE type pet_sex AS ENUM ('male', 'female');

CREATE table address (
    id INT SERIAL PRIMARY KEY,
    street VARCHAR(50),
    number INT,
    complement VARCHAR(30),
    district VARCHAR(50)
);

CREATE table pet (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    type pet_type NOT NULL,
    sex pet_sex NOT NULL,
    address_id INT, FOREIGN KEY (address_id) REFERENCES address(id),
    age INT,
    weight DECIMAL(5,2),
    race VARCHAR(20)
);