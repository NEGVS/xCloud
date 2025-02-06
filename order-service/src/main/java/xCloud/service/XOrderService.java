package xCloud.service;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xCloud.entity.XOrders;
import xCloud.entity.XProducts;
import xCloud.mapper.XOrderMapper;
import xCloud.result.HttpStatusEnum;
import xCloud.result.XResponseResult;

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

   public ResponseEntity< XOrders > selectOrderById( String orderId )
   {
      XOrders xOrders = xOrderMapper.selectOrderById( orderId );
      //htt
      Integer code = HttpStatusEnum.ERROR.getCode();
      return ResponseEntity.ok( xOrders );
   }

   public XResponseResult selectOrderRestTemplateById( String orderId )
   {
      XOrders xOrders = xOrderMapper.selectOrderById( orderId );

      //通过restTemplate调用商品服务
      String url = "http://localhost:8082/xProducts/" + xOrders.getMerchantId();
      XProducts forObject = restTemplate.getForObject( url, XProducts.class );
      System.out.println( "------remote--------" );
      System.out.println( JSONUtil.toJsonStr( forObject ) );
      return XResponseResult.success( xOrders );
   }
}
