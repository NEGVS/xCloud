package xCloud.xProduct.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xCloud.entity.result.XResponseResult;
import xCloud.util.CodeX;
import xCloud.xProduct.entity.XCategories;
import xCloud.xProduct.mapper.XCategoriesMapper;
import xCloud.xProduct.service.IXCategoriesService;

import java.util.Date;
import java.util.List;

/**
 * 商品种类Service业务层处理
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Service
public class XCategoriesServiceImpl implements IXCategoriesService
{
   @Autowired
   private XCategoriesMapper xCategoriesMapper;

   /**
    * 查询商品种类
    *
    * @param categoryId 商品种类主键
    * @return 商品种类
    */
   @Override
   public XCategories selectXCategoriesByCategoryId( Long categoryId )
   {
      return xCategoriesMapper.selectXCategoriesByCategoryId( categoryId );
   }

   /**
    * 查询商品种类列表
    * @param xCategories 商品种类
    * @return 商品种类
    */
   @Override
   public List< XCategories > selectXCategoriesList( XCategories xCategories )
   {
      return xCategoriesMapper.selectXCategoriesList( xCategories );
   }

   /**
    * 新增商品种类
    * @param xCategories 商品种类
    * @return 结果
    */
   @Override
   public XResponseResult insertXCategories(XCategories xCategories)
   {
      if (xCategories == null || xCategories.getName() == null) {
         return XResponseResult.fail("商品种类名称不能为空");
      }
      xCategories.setCreatedTime( new Date() );
      xCategories.setUpdatedTime(new Date());
      int row = xCategoriesMapper.insertXCategories(xCategories);
      if (row > 0) {
         return XResponseResult.success();
      }
      return XResponseResult.fail("null");
   }

   /**
    * 新增商品种类--生成
    * @param xCategories 商品
    * @return 结果
    */
   @Override
   public int insertXGenerate( XCategories xCategories )
   {
      int count = 0;
      for ( int i = 0; i < 100; i++ )
      {
         xCategories.setName( CodeX.generalComplexRandomString() );
         xCategories.setDescription( CodeX.generalComplexRandomString( 25 ) );
         xCategories.setParentCategoryId( 2L );
         xCategories.setCreatedTime( new Date() );
         count += xCategoriesMapper.insertXCategories( xCategories );
      }
      return count;
   }

   /**
    * 修改商品种类
    *
    * @param xCategories 商品种类
    * @return 结果
    */
   @Override
   public XResponseResult updateXCategories(XCategories xCategories)
   {
      if (xCategories == null || xCategories.getCategoryId() == null) {
         return XResponseResult.fail("参数为空");
      }
      // 商品种类是否存在
      if (xCategoriesMapper.selectXCategoriesByCategoryId(xCategories.getCategoryId()) == null) {
         return XResponseResult.fail("商品种类不存在");
      }
      xCategories.setUpdatedTime(new Date());
      int row = xCategoriesMapper.updateXCategories(xCategories);
      if (row > 0) {
         return XResponseResult.success();
      }
      return XResponseResult.fail("null");
   }

   /**
    * 批量删除商品种类
    *
    * @param categoryIds 需要删除的商品种类主键
    * @return 结果
    */
   @Override
   public int deleteXCategoriesByCategoryIds( List<Long> categoryIds )
   {
      return xCategoriesMapper.deleteXCategoriesByCategoryIds( categoryIds );
   }

   /**
    * 删除商品种类信息
    *
    * @param categoryId 商品种类主键
    * @return 结果
    */
   @Override
   public int deleteXCategoriesByCategoryId( Long categoryId )
   {
      return xCategoriesMapper.deleteXCategoriesByCategoryId( categoryId );
   }
}
