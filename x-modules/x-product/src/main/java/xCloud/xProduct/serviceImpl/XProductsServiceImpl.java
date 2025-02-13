package xCloud.xProduct.serviceImpl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xCloud.entity.result.XResponseResult;
import xCloud.util.CodeX;
import xCloud.xProduct.entity.XProducts;
import xCloud.xProduct.mapper.XCategoriesMapper;
import xCloud.xProduct.mapper.XProductsMapper;
import xCloud.xProduct.service.IXProductsService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品Service业务层处理
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Service
@Slf4j
public class XProductsServiceImpl implements IXProductsService
{
   @Resource
   private XProductsMapper xProductsMapper;
   //   @Resource
   //   private XMerchantsMapper xMerchantsMapper;
   @Resource
   private XCategoriesMapper xCategoriesMapper;

   /**
    * 新增商品
    *
    * @param xProducts 商品
    * @return 结果
    */
   @Override
   @Transactional(rollbackFor = Exception.class)
   public XResponseResult insertXProducts( XProducts xProducts )
   {
      log.info( "新增商品入参：{}", JSONUtil.toJsonStr( xProducts ) );
      // 1、必填参数校验
      //      String failMsg = validateParameters(xProducts);
      String failMsg = "";
      if ( StrUtil.isNotBlank( failMsg ) )
      {
         return XResponseResult.fail( failMsg );
      }

      // 2、商品数据落库
      xProducts.setProductId( IdWorker.getId() );
      xProducts.setCreatedTime( new Date() );
      int row = xProductsMapper.insertXProducts( xProducts );
      if ( row > 0 )
      {
         return XResponseResult.success();
      }
      return XResponseResult.fail("");
   }

   /**
    * 新增商品--生成
    * @param xProducts 商品
    * @return 结果
    */
   public int insertGenerateXProducts( XProducts xProducts )
   {
      int count = 0;
      List< XProducts > xProducts1 = xProductsMapper.selectXProductsList( xProducts );
      if ( xProducts1.size() > 99 )
      {
         return 0;
      }
      for ( int i = 0; i < 50; i++ )
      {
         xProducts.setProductId( IdWorker.getId() );
         xProducts.setName( CodeX.generalComplexRandomString() + "商品" );
         xProducts.setDescription( CodeX.generalComplexRandomString( 15 ) );
         xProducts.setPrice( new BigDecimal( 10 ) );
         xProducts.setPrePrice( new BigDecimal( 10 ) );
         xProducts.setCollaboratePrice( new BigDecimal( 10 ) );
         xProducts.setOriginalPrice( new BigDecimal( 10 ) );
         xProducts.setCostPrice( new BigDecimal( 10 ) );
         xProducts.setStock( ( long ) CodeX.generalRandomInt() );
         xProducts.setImage( "https://picsum.photos/500/500" );
         xProducts.setNotes( CodeX.generalComplexRandomString( 4 ) );
         xProducts.setCategoryId( 2L );
         xProducts.setMerchantId( 1L );
         xProducts.setCreatedTime( new Date() );
         count += xProductsMapper.insertXProducts( xProducts );
      }
      return count;
   }

   /**
    * 批量删除商品
    *
    * @param productIds 需要删除的商品主键
    * @return 结果
    */
   @Override
   public int deleteXProductsByProductIds( List< String > productIds )
   {
      return xProductsMapper.deleteXProductsByProductIds( productIds );
   }

   /**
    * 删除商品信息
    *
    * @param productId 商品主键
    * @return 结果
    */
   @Override
   public int deleteXProductsByProductId( Long productId )
   {
      return xProductsMapper.deleteXProductsByProductId( productId );
   }

   /**
    * 修改商品
    *
    * @param xProducts 商品
    * @return 结果
    */
   @Override
   public XResponseResult updateXProducts( XProducts xProducts )
   {
      // 1.1、参数校验
      //      if ( xProducts == null )
      //      {
      //         return XResponseResult.fail( "参数为空" );
      //      }
      //      if ( xProducts.getProductId() == null )
      //      {
      //         return XResponseResult.fail( "商品ID为空" );
      //      }
      //      // 1.2、商品是否存在
      //      if ( xProductsMapper.selectXProductsByProductId( xProducts.getProductId() ) == null )
      //      {
      //         return XResponseResult.fail( "商品不存在" );
      //      }
      try
      {
         xProducts.setUpdatedTime( new Date() );
         int row = xProductsMapper.updateXProducts( xProducts );
         log.info( "\n修改成功个数：{}", row );
         if ( row > 0 )
         {
            return XResponseResult.success();
         }
      }
      catch ( Exception e )
      {
         log.info( "\n修改商品失败,原因：{}", e.getMessage() );
      }

      return XResponseResult.fail( "" );
   }

   /**
    * 修改商品-库存
    * @param xProducts 商品
    * @return 结果
    */

   @Override
   public int updateInventory( XProducts xProducts )
   {
      return xProductsMapper.updateInventory( xProducts );
   }

   /**
    * 查询商品
    *
    * @param productId 商品主键
    * @return 商品
    */
   @Override
   public XProducts selectXProductsByProductId( Long productId )
   {
      return xProductsMapper.selectXProductsByProductId( productId );
   }

   /**
    * 查询商品列表
    *
    * @param xProducts 商品
    * @return 商品
    */
   @Override
   public List< XProducts > selectXProductsList( XProducts xProducts )
   {
      return xProductsMapper.selectXProductsList( xProducts );
   }

   /**
    * 校验参数-add
    * @param xProducts
    * @return
    */
   private String validateParameters( XProducts xProducts )
   {

      if ( xProducts == null )
      {
         return "参数为空";
      }
      // 商品名称校验
      if ( StrUtil.isBlank( xProducts.getName() ) )
      {
         return "商品名称不能为空";
      }
      if ( xProducts.getName().length() > 50 )
      {
         return "商品名称不能超过50个字符";
      }
      // 商品描述校验
      if ( StrUtil.isNotBlank( xProducts.getDescription() ) && xProducts.getDescription().length() > 100 )
      {
         return "商品描述不能超过100个字符";
      }
      // 商品价格校验
      if ( xProducts.getPrice() == null || xProducts.getPrice().compareTo( BigDecimal.ZERO ) < 0 )
      {
         return "商品价格不能小于0";
      }
      // 商品合作价格
      if ( xProducts.getCollaboratePrice() == null || xProducts.getCollaboratePrice().compareTo( BigDecimal.ZERO ) < 0 )
      {
         return "商品合作价格不能小于0";
      }
      // 商品成本价格
      if ( xProducts.getCostPrice() == null || xProducts.getCostPrice().compareTo( BigDecimal.ZERO ) < 0 )
      {
         return "商品成本价格不能小于0";
      }
      //      if (xProducts.getCostPrice().compareTo(xProducts.getPrice()) > 0) {
      //         return "商品成本价格不能大于商品价格";
      //      }
      //      if (xProducts.getCostPrice().compareTo(xProducts.getCollaboratePrice()) > 0) {
      //         return "商品成本价格不能大于商品合作价格";
      //      }
      if ( xProducts.getOriginalPrice() == null || xProducts.getOriginalPrice().compareTo( BigDecimal.ZERO ) < 0 )
      {
         return "商品库存不能小于0";
      }
      // 商家ID
      if ( xProducts.getMerchantId() == null )
      {
         return "商家ID不能为空";
      }
      // 种类
      if ( xProducts.getCategoryId() == null )
      {
         return "商品种类不能为空";
      }
      //      // 1.2、商家是否存在
      //      if ( xMerchantsMapper.selectXMerchantsByMerchantId( xProducts.getMerchantId() ) == null )
      //      {
      //         return "商家不存在";
      //      }
      // 1.3、商品种类是否存在
      if ( xCategoriesMapper.selectXCategoriesByCategoryId( xProducts.getCategoryId() ) == null )
      {
         return "商品种类不存在";
      }
      return null;
   }

}
