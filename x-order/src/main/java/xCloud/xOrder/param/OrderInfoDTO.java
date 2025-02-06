package xCloud.xOrder.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 生成订单之后的订单详情DTO
 */
@Data
public class OrderInfoDTO {

    @Schema (description = "订单详情", name = "orderDetailList")
    List<OrderDetailInfoDTO> orderDetailList;
    // 收货地址
    @Schema (description = "收货地址", name = "shippingAddress")
    private String shippingAddress;
    /** 订单总价格 */
    @Schema (description = "订单总价格", name = "amount")
    private BigDecimal amount;

    @Schema (description = "骑手id", name = "riderId")
    private String riderId;

    @Schema (description = "支付方式", name = "paymentType")
    private String paymentType;

    @Schema (description = "订单状态", name = "status")
    private Integer status;

    @Schema (description = "订单备注", name = "notes")
    private String notes;

    @Schema (description = "订单id", name = "orderId")
    private Long orderId;

    @Schema (description = "订单创建时间", name = "createdTime")
    private String createdTime;

    @Schema (description = "二维码链接", name = "qrCode")
    private String qrCode;
}
