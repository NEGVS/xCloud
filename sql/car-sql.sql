CREATE TABLE car_manufacturers
(
    manufacturer_id       INT AUTO_INCREMENT PRIMARY KEY COMMENT '厂商ID',       
    manufacturer_name     VARCHAR(255) NOT NULL COMMENT '厂商名称',               
    established_year YEAR COMMENT '成立年份',                                    
    headquarters_location VARCHAR(255) COMMENT '总部地点'                         
);


CREATE TABLE car_models
(
    model_id        INT AUTO_INCREMENT PRIMARY KEY COMMENT '车型ID', 
    manufacturer_id INT          NOT NULL COMMENT '厂商ID',          
    model_name      VARCHAR(255) NOT NULL COMMENT '车型名称',        
    launch_year YEAR COMMENT '车型发布年份'                          
)COMMENT = '购物车表';


CREATE TABLE monthly_sales
(
    sales_id          INT AUTO_INCREMENT PRIMARY KEY COMMENT '销售记录ID',     
    manufacturer_name VARCHAR(255) NOT NULL COMMENT '厂商名称',          
    model_name        VARCHAR(255) NOT NULL COMMENT '车型名称',       
    sales_month       DATE         NOT NULL COMMENT '销售月份',             
    units_sold        INT          NOT NULL  COMMENT '销量'         
)COMMENT = '购物车表';
--  查询某个厂商在某年内的所有车型销量总和：
SELECT SUM(units_sold) AS total_sales
FROM monthly_sales
WHERE manufacturer_name = '特斯拉' AND YEAR (sales_month) = 2024;

