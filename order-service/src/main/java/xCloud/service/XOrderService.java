package xCloud.service;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xCloud.entity.XOrders;
import xCloud.entity.XProducts;
import xCloud.mapper.XOrderMapper;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 16:22
 *@ClassName XOrderService
 */
@Service
public class XOrderService
{
   @Autowired
   private XOrderMapper xOrderMapper;
   @Autowired
   private RestTemplate restTemplate;

   public XOrders selectOrderById( String orderId )
   {
      XOrders xOrders = xOrderMapper.selectOrderById( orderId );
      return xOrders;
   }

   public XOrders selectOrderRestTemplateById( String orderId )
   {
      XOrders xOrders = xOrderMapper.selectOrderById( orderId );

      //通过restTemplate调用商品服务
      String url = "http://localhost:8082/xProducts/" + xOrders.getMerchantId();
      XProducts forObject = restTemplate.getForObject( url, XProducts.class );
      System.out.println( "------remote--------" );
      System.out.println( JSONUtil.toJsonStr( forObject ) );
      return xOrders;
   }
}
