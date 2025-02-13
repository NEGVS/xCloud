package xCloud.xProduct.service;


import xCloud.entity.result.XResponseResult;
import xCloud.xProduct.entity.XCategories;

import java.util.List;

/**
 * 商品种类Service接口
 *
 * @author AndyFan
 * @date 2024-11-28
 */
public interface IXCategoriesService
{
   /**
    * 查询商品种类
    *
    * @param categoryId 商品种类主键
    * @return 商品种类
    */
   public XCategories selectXCategoriesByCategoryId( Long categoryId );

   /**
    * 查询商品种类列表
    *
    * @param xCategories 商品种类
    * @return 商品种类集合
    */
   public List< XCategories > selectXCategoriesList( XCategories xCategories );

   /**
    * 新增商品种类
    *
    * @param xCategories 商品种类
    * @return 结果
    */
   public XResponseResult insertXCategories(XCategories xCategories );
  /**
    * 新增商品种类
    *
    * @param xCategories 商品种类
    * @return 结果
    */
   public int insertXGenerate( XCategories xCategories );

   /**
    * 修改商品种类
    *
    * @param xCategories 商品种类
    * @return 结果
    */
   public XResponseResult updateXCategories( XCategories xCategories );

   /**
    * 批量删除商品种类
    *
    * @param categoryIds 需要删除的商品种类主键集合
    * @return 结果
    */
   public int deleteXCategoriesByCategoryIds(  List<Long> categoryIds );

   /**
    * 删除商品种类信息
    *
    * @param categoryId 商品种类主键
    * @return 结果
    */
   public int deleteXCategoriesByCategoryId( Long categoryId );
}
