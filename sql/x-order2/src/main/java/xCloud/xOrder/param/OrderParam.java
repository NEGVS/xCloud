package xCloud.xOrder.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单入参
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderParam {

    /** 商品ID */
    @Excel(name = "商品ID")
    private List<OrderProductParam> productList;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String shippingAddress;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal amount;

    /** 骑手id */
    @Excel(name = "骑手id")
    private String riderId;

    /** 支付方式*/
    @Excel(name = "支付方式")
    private String paymentType;
}
