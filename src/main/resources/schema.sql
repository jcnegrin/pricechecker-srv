CREATE DATABASE IF NOT EXISTS pricecheker;

USE pricecheker;

CREATE TABLE user (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  user_name VARCHAR(100) UNIQUE,
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100),
  registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shop (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  url VARCHAR(255)
);

CREATE TABLE category (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE product (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  brand VARCHAR(50),
  description TEXT,
  image_url VARCHAR(255),
  shop_id VARCHAR(36),
  category_id VARCHAR(36),
  CONSTRAINT fk_product_shop FOREIGN KEY (shop_id) REFERENCES shop(id) ON DELETE SET NULL,
  CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);

CREATE TABLE price (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  amount DECIMAL(10, 2),
  update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  product_id VARCHAR(36),
  CONSTRAINT fk_price_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE favorite (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  user_id VARCHAR(36),
  product_id VARCHAR(36),
  CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  CONSTRAINT fk_favorite_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);


--

INSERT INTO shop (id, name, url)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Mercadona', 'https://tienda.mercadona.es/categories/112');

INSERT INTO shop (id, name, url)
VALUES ('987e6543-b21a-34c5-d987-123456789012', 'Dia', 'https://www.dia.es');