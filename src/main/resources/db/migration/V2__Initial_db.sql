CREATE TABLE users (
                       id         bigserial PRIMARY KEY,
                       first_name varchar(100) NOT NULL,
                       last_name  varchar(100) NOT NULL,
                       balance    numeric(12,2) NOT NULL DEFAULT 0 CHECK (balance >= 0)
);

CREATE TABLE admins (
                        id    bigserial PRIMARY KEY,
                        login varchar(100) NOT NULL UNIQUE,
                        name  varchar(100) NOT NULL
);

CREATE TABLE coffee_machines (
                                 id bigserial PRIMARY KEY
);

CREATE TABLE drinks (
                        id                bigserial PRIMARY KEY,
                        name              varchar(100) NOT NULL,
                        price             numeric(12,2) NOT NULL CHECK (price >= 0),
                        portions          integer NOT NULL DEFAULT 0 CHECK (portions >= 0),
                        coffee_machine_id bigint
);

CREATE TABLE ingredients (
                             id                bigserial PRIMARY KEY,
                             name              varchar(100) NOT NULL,
                             price             numeric(12,2) NOT NULL CHECK (price >= 0),
                             portions          integer NOT NULL DEFAULT 0 CHECK (portions >= 0),
                             coffee_machine_id bigint
);

CREATE TABLE orders (
                        id      bigserial PRIMARY KEY,
                        user_id bigint NOT NULL,
                        total   numeric(12,2) NOT NULL DEFAULT 0
);

CREATE TABLE order_items (
                             id       bigserial PRIMARY KEY,
                             order_id bigint NOT NULL,
                             drink_id bigint NOT NULL,
                             quantity integer NOT NULL CHECK (quantity > 0)
);

CREATE TABLE order_item_ingredients (
                                        order_item_id bigint NOT NULL,
                                        ingredient_id bigint NOT NULL,
                                        PRIMARY KEY (order_item_id, ingredient_id)
);

ALTER TABLE drinks
    ADD CONSTRAINT fk_drinks_coffee_machine
        FOREIGN KEY (coffee_machine_id) REFERENCES coffee_machines(id);

ALTER TABLE ingredients
    ADD CONSTRAINT fk_ingredients_coffee_machine
        FOREIGN KEY (coffee_machine_id) REFERENCES coffee_machines(id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_drink
        FOREIGN KEY (drink_id) REFERENCES drinks(id);

ALTER TABLE order_item_ingredients
    ADD CONSTRAINT fk_oii_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE;

ALTER TABLE order_item_ingredients
    ADD CONSTRAINT fk_oii_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES ingredients(id);