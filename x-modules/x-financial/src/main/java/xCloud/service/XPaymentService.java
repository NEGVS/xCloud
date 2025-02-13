package xCloud.service;

import java.math.BigDecimal;

// 支付服务接口
public interface XPaymentService
{
   boolean processPayment( BigDecimal amount, String paymentId );
}
