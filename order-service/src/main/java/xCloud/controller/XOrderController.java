package xCloud.controller;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xCloud.entity.XOrders;
import xCloud.service.XOrderService;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 16:19
 *@ClassName XOrderController
 */
@RestController
@RequestMapping("/xOrder")
@Slf4j
public class XOrderController
{
   @Resource
   private XOrderService xOrderService;

   @RequestMapping("/{orderId}")
   public XOrders getOrderById( @PathVariable("orderId") String orderId )
   {
      log.info( "请求参数：" + orderId );
      XOrders xOrders = xOrderService.selectOrderById( orderId );
      log.info( JSONUtil.toJsonStr( xOrders ) );
      XOrders xOrders1 = xOrderService.selectOrderRestTemplateById( orderId );
      log.info( "========" );
      log.info( JSONUtil.toJsonStr( xOrders1 ) );
      return xOrders1;
   }
}
