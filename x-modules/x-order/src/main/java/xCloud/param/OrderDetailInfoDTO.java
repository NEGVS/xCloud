package xCloud.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
//@Schema(description = "订单详情")
public class OrderDetailInfoDTO
{
   /** 商品ID */
   private Long productId;
   /** 商品名称 */
   private String productName;
   /** 商品数量 */
   private Long quantity;
   /** 商品价格 */
   private BigDecimal price;
   /** 商品总价 */
   private BigDecimal totalPrice;
}
