package xCloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品对象 x_products
 *
 * @author andy fan
 * @date 2024-11-28
 * 高并发处理
 * 在高并发情况下，为了避免超卖问题，可以通过分布式锁或数据库乐观锁来保护库存的扣减操作，防止多个订单同时扣减同一商品的库存。
 */
@Data
// 忽略空值
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class XProducts
{
   private static final long serialVersionUID = 1L;

   /** 商品ID,雪花算法（Snowflake）被广泛用于生成唯一的ID。这些ID通常是Long类型的整数。然而，当这些ID从后端传递到前端时，JavaScript的精度限制可能会导致精度丢失，特别是ID的后三位无法正确表示。本文将探讨这一问题的原因，并提供三种解决方案。
    * 在后端，雪花算法生成的ID是一个64位的Long类型整数
    * 当这个ID传递到前端时，JavaScript的Number类型无法精确表示如此大的整数。JavaScript的Number类型使用双精度浮点数表示，其有效精度范围是 (-2^{53}) 到 (2^{53})（不包含边界）。因此，当ID超过这个范围时，精度就会丢失,
    * 在后端，可以通过Jackson库将Long类型的ID转换为String类型，从而避免精度丢失。具体实现如下,使用@JsonFormat注解将Long类型的ID序列化为String：后端返回的JSON数据中，ID将以字符串形式表示，前端接收时不会丢失精度。
    * 方案二：自定义JSON配置类
    *
    * */
   @JsonFormat(shape = JsonFormat.Shape.STRING)
   @JsonSerialize(contentUsing = ToStringSerializer.class)
   @JsonIgnoreProperties(ignoreUnknown = true)
   private Long productId;

   /** 商品名称 */
   //@Excel(name = "商品名称")
   private String name;

   /** 商品描述 */
   //@Excel(name = "商品描述")
   private String description;

   /** 售卖价=优惠价 */
   //@Excel(name = "售卖价")
   private BigDecimal price;

   /** 营销原价 */
   //@Excel(name = "营销原价")
   private BigDecimal prePrice;

   /** 合作价格 */
   //@Excel(name = "合作价格")
   private BigDecimal collaboratePrice;

   /** 真实原价 */
   //@Excel(name = "真实原价")
   private BigDecimal originalPrice;

   /** 成本价格 */
   //@Excel(name = "成本价格")
   private BigDecimal costPrice;

   /** 商品库存数量，-1：无限 */
   //@Excel(name = "库存")
   private Long stock;

   /** 商品图片 */
   //   //@Excel(name = "商品图片")
   private String image;

   /** 备注 */
   //@Excel(name = "备注")
   private String notes;

   /** 商品种类ID */
   //@Excel(name = "商品种类")
   private Long categoryId;

   /** 商家ID */
   //@Excel(name = "商家")
   private Long merchantId;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   //@Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   //@Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
   private Date updatedTime;

   private Long version;  // 乐观锁版本号

}
