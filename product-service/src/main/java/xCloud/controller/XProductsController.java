package xCloud.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xCloud.entity.XProducts;
import xCloud.service.XProductsService;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 16:59
 *@ClassName XProducts
 */
@RestController
@RequestMapping("/xProducts")
public class XProductsController
{

   @Resource
   private XProductsService xProductsService;

   @RequestMapping("/{productId}")
   public XProducts getProducts( @PathVariable("productId") String productId )
   {
      return xProductsService.selectProductById( productId );
   }
}
