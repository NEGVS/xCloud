package xCloud.xProduct.service;


import xCloud.result.XResponseResult;
import xCloud.xProduct.entity.XProducts;

import java.util.List;

/**
 * 商品Service接口
 *
 * @author AndyFan
 * @date 2024-11-28
 */
public interface IXProductsService
{
   /**
    * 查询商品
    *
    * @param productId 商品主键
    * @return 商品
    */
   public XProducts selectXProductsByProductId( Long productId );

   /**
    * 查询商品列表
    *
    * @param xProducts 商品
    * @return 商品集合
    */
   public List< XProducts > selectXProductsList( XProducts xProducts );

   /**
    * 新增商品
    *
    * @param xProducts 商品
    * @return 结果
    */
   public XResponseResult insertXProducts(XProducts xProducts );

   /**
    * 新增商品--生成
    *
    * @param xProducts 商品
    * @return 结果
    */
   public int insertGenerateXProducts( XProducts xProducts );

   /**
    * 修改商品
    *
    * @param xProducts 商品
    * @return 结果
    */
   public XResponseResult updateXProducts( XProducts xProducts );
   /**
    * 修改商品
    *
    * @param xProducts 商品
    * @return 结果
    */
   public int updateInventory( XProducts xProducts );

   /**
    * 批量删除商品
    *
    * @param productIds 需要删除的商品主键集合
    * @return 结果
    */
   public int deleteXProductsByProductIds( List<String> productIds );

   /**
    * 删除商品信息
    *
    * @param productId 商品主键
    * @return 结果
    */
   public int deleteXProductsByProductId( Long productId );
}
