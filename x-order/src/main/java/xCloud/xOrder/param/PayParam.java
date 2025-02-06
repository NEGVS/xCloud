package xCloud.xOrder.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema( description = "支付参数" )
public class PayParam {
    @Schema( description = "订单id" ,name = "orderId")
    private Long orderId;

    @Schema( description = "支付类型" ,name = "paymentType")
    private String paymentType;

    @Schema( description = "支付金额" ,name = "amount")
    private BigDecimal amount;

    @Schema( description = "支付备注" ,name = "notes")
    private String notes;

    @Schema( description = "支付时间" ,name = "payTime")
    private String payTime;

    @Schema( description = "支付流水号" ,name = "paymentId")
    private String paymentId;

}
