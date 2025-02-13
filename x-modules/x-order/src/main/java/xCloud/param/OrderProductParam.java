package xCloud.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 生成订单参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "生成订单参数")
public class OrderProductParam
{
   /** 商品ID */
   @Schema(description = "商品ID", name = "productId")
   private Long productId;

   /** 商品数量 */
   @Schema(description = "商品数量", name = "quantity")
   private Long quantity;

   /** 商品数量 */
   @Schema(description = "商品单价", name = "price")
   private BigDecimal price;
}
