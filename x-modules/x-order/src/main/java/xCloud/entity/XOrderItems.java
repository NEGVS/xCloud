package xCloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细对象 x_order_items
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Data
public class XOrderItems extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 订单明细ID */
   private Long orderItemId;

   /** 订单ID */
   @Excel(name = "订单ID")
   private Long orderId;

   /** 商品ID */
   @Excel(name = "商品ID")
   private Long productId;

   /** 商品数量 */
   @Excel(name = "商品数量")
   private Long quantity;

   /** 商品价格 */
   @Excel(name = "商品价格")
   private BigDecimal price;

   /** 商品小计 */
   @Excel(name = "商品小计")
   private BigDecimal totalPrice;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date updatedTime;

}
