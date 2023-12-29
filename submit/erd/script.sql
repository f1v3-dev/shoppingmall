DROP TABLE IF EXISTS Cart;
DROP TABLE IF EXISTS OrderDetails;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Products_Categories;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Users;


CREATE TABLE Users
(
    user_id         varchar(50)  NOT NULL COMMENT '아이디',
    user_name       varchar(50)  NOT NULL COMMENT '이름',
    user_password   varchar(200) NOT NULL COMMENT 'mysql password 사용',
    user_birth      varchar(8)   NOT NULL COMMENT '생년월일 : 19840503',
    user_auth       varchar(10)  NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
    user_point      int          NOT NULL COMMENT 'default : 1000000',
    created_at      datetime     NOT NULL COMMENT '가입일자',
    latest_login_at datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='회원';

CREATE TABLE Address
(
    address_id       int auto_increment,
    user_id          varchar(50)  NOT NULL,
    zip_code         char(5)      NOT NULL,
    detailed_address varchar(200) NOT NULL,
    PRIMARY KEY (address_id),
    CONSTRAINT fk_Address_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='주소';

CREATE TABLE Categories
(
    category_id   INT auto_increment,
    category_name varchar(50) NOT NULL UNIQUE,

    CONSTRAINT pk_Categories PRIMARY KEY (category_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='카테고리';


CREATE TABLE Products
(
    product_id    INT auto_increment,
    model_number  varchar(10)  NOT NULL UNIQUE,
    model_name    varchar(120) NOT NULL,
    product_image varchar(30),
    unit_cost     INT          NOT NULL,
    description   text,

    CONSTRAINT pk_Products PRIMARY KEY (product_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='상품';


CREATE TABLE Products_Categories
(
    product_id  INT,
    category_id INT,

    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES Products (product_id),
    FOREIGN KEY (category_id) REFERENCES Categories (category_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='상품_카테고리';

CREATE TABLE Reviews
(
    review_id  int auto_increment,
    product_id int         NOT NULL,
    user_id    varchar(50) NOT NULL,
    rating     int         NOT NULL,
    comment    text,

    CONSTRAINT pk_Reviews PRIMARY KEY (review_id),
    CONSTRAINT fk_Reviews_Products FOREIGN KEY (product_id) REFERENCES Products (product_id),
    CONSTRAINT fk_Reviews_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='리뷰';

CREATE TABLE Orders
(
    order_id   int auto_increment,
    user_id    varchar(50) NOT NULL,
    order_date Datetime DEFAULT NOW(),
    ship_date  Datetime,

    CONSTRAINT pk_Orders PRIMARY KEY (order_id),
    CONSTRAINT fk_Orders_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='주문';

CREATE TABLE OrderDetails
(
    order_id   int,
    product_id int,
    quantity   int NOT NULL,
    unit_cost  int NOT NULL,

    CONSTRAINT pk_OrderDetails PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    CONSTRAINT fk_OrderDetails_Products FOREIGN KEY (product_id) REFERENCES Products (product_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='주문상세';

CREATE TABLE Cart
(
    cart_id      INT auto_increment,
    user_id      VARCHAR(50) NOT NULL,
    product_id   INT         NOT NULL,
    quantity     INT         NOT NULL,
    date_created DATETIME DEFAULT NOW(),

    CONSTRAINT pk_Cart PRIMARY KEY (cart_id),
    CONSTRAINT fk_Cart_Users FOREIGN KEY (user_id) REFERENCES Users (user_id),
    CONSTRAINT fk_Cart_Products FOREIGN KEY (product_id) REFERENCES Products (product_id),
    CONSTRAINT uc_Cart UNIQUE (user_id, product_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='장바구니';

CREATE TABLE PointHistory
(
    point_id INT auto_increment,
    user_id VARCHAR(50) NOT NULL,
    point INT NOT NULL,
    description VARCHAR(100) NOT NULL,
    registration_date DATETIME DEFAULT NOW(),
    CONSTRAINT pk_PointHistory PRIMARY KEY (point_id),
    CONSTRAINT fk_PointHistory_Users FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE
);

ALTER TABLE PointHistory CHANGE COLUMN date_updated registration_date DATETIME DEFAULT NOW();
/*==============================================================*/

INSERT INTO Categories
VALUES (1, 'Clothes', NULL);

INSERT INTO Categories
VALUES (2, 'Shoes', NULL);

INSERT INTO Categories
VALUES (3, 'Accessories', NULL);

INSERT INTO Categories
VALUES (NULL, 'Top', 1);

INSERT INTO Categories
VALUES (NULL, 'Pants', 1);

INSERT INTO Categories
VALUES (NULL, 'Outer', 1);

INSERT INTO Categories
VALUES (NULL, 'Shirts', 2);

INSERT INTO Categories
VALUES (NULL, 'Jeans', 3);

INSERT INTO Categories
VALUES (NULL, 'Sneakers', 2);

INSERT INTO Categories
VALUES (NULL, 'Headwear', 3);

INSERT INTO Categories
VALUES (NULL, 'Bag', 3)