package xCloud.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单对象 x_orders
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Data
public class XOrders extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 订单id */
   private Long orderId;

   /** 用户id */
   @Excel(name = "用户id")
   private Long userId;

   /** 商家id */
   @Excel(name = "商家id")
   private Long merchantId;

   /** 订单总金额 */
   @Excel(name = "订单总金额")
   private BigDecimal amount;

   /** 订单状态('pending'1, 'paid'2, 'shipped'3, 'completed'4, 'canceled'5) */
   @Excel(name = "订单状态('pending'1, 'paid'2, 'shipped'3, 'completed'4, 'canceled'5)")
   private Integer status;

   /** 支付id */
   @Excel(name = "支付id")
   private String paymentId;

   /** 购物车信息 */
   @Excel(name = "购物车信息")
   private String shoppingJson;

   /** 付款时间 */
   @JsonFormat(pattern = "yyyy-MM-dd")
   @Excel(name = "付款时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date payTime;

   /** 骑手id */
   @Excel(name = "骑手id")
   private String riderId;

   /** 收货地址 */
   @Excel(name = "收货地址")
   private String shippingAddress;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date updatedTime;

   /**
    * for application
    */
   @TableField(exist = false)
   private Integer retryCount;

   @TableField(exist = false)
   private List< XOrderItems > items = new ArrayList<>();

}
