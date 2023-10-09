CREATE TABLE products_tb (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    value DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT check_description_min_length CHECK (CHAR_LENGTH(description) >= 10),
    CONSTRAINT check_value_positive CHECK (value > 0)
);
