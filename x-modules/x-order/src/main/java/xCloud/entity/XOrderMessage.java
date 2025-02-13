package xCloud.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 订单消息模型
 * @Author Andy Fan
 * @Date 2024/12/4 13:24
 * @ClassName OrderMessage
 */
@Data
public class XOrderMessage
{
   private Long userId;
   private Integer merchantId;
   private BigDecimal amount;
   private List< XOrderItems > items;
}
