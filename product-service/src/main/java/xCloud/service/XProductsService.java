package xCloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xCloud.entity.XProducts;
import xCloud.mapper.XProductsMapper;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 17:01
 *@ClassName XProductsService
 */
@Service
public class XProductsService
{

   @Autowired
   private XProductsMapper xProductsMapper;

   public XProducts selectProductById( String id )
   {
      return xProductsMapper.selectProductById( id );
   }

}
