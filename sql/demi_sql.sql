-- 正式项目数据库，均以【x_】开头。
CREATE TABLE x_merchants
(
    merchant_id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '商家ID',
    password         VARCHAR(200) DEFAULT '123' NOT NULL COMMENT '密码',
    name             VARCHAR(255)               NOT NULL COMMENT '商家名称',
    logo             VARCHAR(500) DEFAULT NULL COMMENT 'logo',
    image            VARCHAR(500) DEFAULT NULL COMMENT '商家图片',
    address          VARCHAR(500) DEFAULT NULL COMMENT '地址',
    open_id          varchar(200) DEFAULT NULL comment 'openID',
    status           tinyint      default '1'   not null comment '状态',
    sort             tinyint null comment '排序',
    packaging_rating decimal(10, 2)             not null comment '包装评分',
    quantity_rating  decimal(10, 2)             not null comment '分量评分',
    taste_rating     decimal(10, 2)             not null comment '口味评分',
    phone            VARCHAR(20) COMMENT '电话',
    email            VARCHAR(50) COMMENT '邮箱',
    created_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT ='商家表';

CREATE TABLE x_orders
(
    order_id         int auto_increment primary key comment '订单id',
    user_id          bigint                 not null comment '用户id',
    merchant_id      int                    not null comment '商家id',
    amount           decimal(10, 2)         not null comment '订单总金额',
    status           tinyint     default null comment '订单状态(''pending''1, ''paid''2, ''shipped''3, ''completed''4, ''canceled''5)',
    payment_id       varchar(36) default '' not null comment '支付id',
    shopping_json    longtext null comment '购物车信息',
    pay_time         datetime null comment '付款时间',
    rider_id         varchar(36) default '' not null comment '骑手id',
    shipping_address varchar(255) comment '收货地址',
    created_time     timestamp   default current_timestamp comment '创建时间',
    updated_time     timestamp   default current_timestamp on update current_timestamp comment '更新时间',
    foreign key (user_id) references sys_user (user_id) on delete cascade,
    foreign key (merchant_id) references x_merchants (merchant_id) on delete cascade,
    index (status) comment '索引：订单状态'
) COMMENT ='订单表';

CREATE TABLE x_categories
(
    category_id        INT AUTO_INCREMENT PRIMARY KEY COMMENT '商品种类ID',
    name               VARCHAR(255) NOT NULL COMMENT '商品种类名称',
    description        TEXT COMMENT '商品种类描述',
    parent_category_id INT COMMENT '父级商品种类ID',
    created_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (parent_category_id) REFERENCES x_categories (category_id) ON DELETE SET NULL
) COMMENT ='商品种类表';

CREATE TABLE x_sites
(
    site_id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '站点ID',
    name         VARCHAR(255) NOT NULL COMMENT '站点名称',
    location     VARCHAR(255) COMMENT '站点位置',
    merchant_id  INT COMMENT '商家ID',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (merchant_id) REFERENCES x_merchants (merchant_id) ON DELETE SET NULL
) COMMENT ='站点表';


CREATE TABLE x_financials
(
    financial_id     INT AUTO_INCREMENT PRIMARY KEY COMMENT '财务ID',
    merchant_id      INT            NOT NULL COMMENT '商家ID',
    amount           DECIMAL(10, 2) NOT NULL COMMENT '财务金额',
    transaction_type tinyint   DEFAULT null COMMENT '交易类型：''Revenue'', ''Expense''',
    created_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (merchant_id) REFERENCES x_merchants (merchant_id) ON DELETE CASCADE,
    INDEX (merchant_id, transaction_type, created_time) COMMENT '索引：商家ID和交易类型'
) COMMENT ='财务表';



CREATE TABLE x_products
(
    product_id        INT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    name              VARCHAR(255)   NOT NULL COMMENT '商品名称',
    description       TEXT COMMENT '商品描述',
    price             DECIMAL(10, 2) NOT NULL COMMENT '商品价格：售卖价=优惠价',
    pre_price         DECIMAL(10, 2) NOT NULL COMMENT '商品价格：营销原价',
    collaborate_price DECIMAL(10, 2) NOT NULL COMMENT '商品价格：合作价格',
    original_price    DECIMAL(10, 2) NOT NULL COMMENT '商品价格：真实原价',
    cost_price        DECIMAL(10, 2) NOT NULL COMMENT '商品价格：成本价格',
    stock             INT          DEFAULT -1 COMMENT '商品库存数量，-1：无限',
    image             VARCHAR(500) DEFAULT NULL COMMENT '商品图片',
    notes             VARCHAR(255) DEFAULT NULL COMMENT '备注',
    category_id       INT COMMENT '商品种类ID',
    merchant_id       INT COMMENT '商家ID',
    created_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES x_categories (category_id) ON DELETE SET NULL,
    FOREIGN KEY (merchant_id) REFERENCES x_merchants (merchant_id) ON DELETE CASCADE,
    INDEX ( name) COMMENT '索引：商品名称'
) COMMENT ='商品表';


CREATE TABLE x_order_items
(
    order_item_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '订单明细ID',
    order_id      INT            NOT NULL COMMENT '订单ID',
    product_id    INT            NOT NULL COMMENT '商品ID',
    quantity      INT            NOT NULL DEFAULT 1 COMMENT '商品数量',
    price         DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    created_time  TIMESTAMP               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time  TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (order_id) REFERENCES x_orders (order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES x_products (product_id) ON DELETE CASCADE,
    INDEX (order_id, product_id) COMMENT '复合索引：订单ID和商品ID'
) COMMENT ='订单明细表';



