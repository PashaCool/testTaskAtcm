CREATE SCHEMA prd;

CREATE TABLE IF NOT EXISTS prd.products (
            id INT NOT NULL PRIMARY KEY,
            product_title VARCHAR(200) NOT NULL UNIQUE,
            product_price DECIMAL NOT NULL CONSTRAINT positive_price CHECK (product_price > 0),
            exists_count numeric CONSTRAINT positive_count CHECK (exists_count > 0)
            );

INSERT INTO prd.products(id, product_title, product_price, exists_count) VALUES
            (1, 'Nesquik', 25.0, 57),
            (2, 'Coca-Cola', 12.34, 25),
            (3, 'Kit Kat', 7.20, 350),
            (4, 'Kinder', 3.7, 230),
            (5, 'Oreo', 4.32, 147),
            (6, 'Cheese', 3.12, 30),
            (7, 'Doritos', 10.50, 35),
            (8, 'Beans', 3.21, 67),
            (9, 'Cucumber', 0.67, 29),
            (10, 'Garlic', 0.99, 165)
            ;