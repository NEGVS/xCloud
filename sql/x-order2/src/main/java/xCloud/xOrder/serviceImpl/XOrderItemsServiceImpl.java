package xCloud.xOrder.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xCloud.util.CodeX;
import xCloud.xOrder.entity.XOrderItems;
import xCloud.xOrder.mapper.XOrderItemsMapper;
import xCloud.xOrder.service.IXOrderItemsService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单明细Service业务层处理
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Service
public class XOrderItemsServiceImpl implements IXOrderItemsService
{
   @Autowired
   private XOrderItemsMapper xOrderItemsMapper;

   /**
    * 查询订单明细
    *
    * @param orderItemId 订单明细主键
    * @return 订单明细
    */
   @Override
   public XOrderItems selectXOrderItemsByOrderItemId( Long orderItemId )
   {
      return xOrderItemsMapper.selectXOrderItemsByOrderItemId( orderItemId );
   }

   /**
    * 查询订单明细列表
    *
    * @param xOrderItems 订单明细
    * @return 订单明细
    */
   @Override
   public List< XOrderItems > selectXOrderItemsList( XOrderItems xOrderItems )
   {
      return xOrderItemsMapper.selectXOrderItemsList( xOrderItems );
   }

   /**
    * 新增订单明细
    *
    * @param xOrderItems 订单明细
    * @return 结果
    */
   @Override
   public int insertXOrderItems( XOrderItems xOrderItems )
   {
      xOrderItems.setCreatedTime( new Date() );

      return xOrderItemsMapper.insertXOrderItems( xOrderItems );
   }

   /**
    * 新增订单明细--生成
    * @param xOrderItems x
    * @return 结果
    */
   public int insertXGenerate( XOrderItems xOrderItems )
   {
      int count = 0;
      for ( int i = 0; i < 100; i++ )
      {
         xOrderItems.setQuantity( 2L );
         xOrderItems.setOrderId( 1L );
         xOrderItems.setProductId( 1L );
         xOrderItems.setPrice( new BigDecimal( CodeX.generalRandomInt() ) );
         xOrderItems.setCreatedTime( new Date() );
         count += xOrderItemsMapper.insertXOrderItems( xOrderItems );
      }
      return count;
   }

   /**
    * 修改订单明细
    *
    * @param xOrderItems 订单明细
    * @return 结果
    */
   @Override
   public int updateXOrderItems( XOrderItems xOrderItems )
   {
      return xOrderItemsMapper.updateXOrderItems( xOrderItems );
   }

   /**
    * 批量删除订单明细
    *
    * @param orderItemIds 需要删除的订单明细主键
    * @return 结果
    */
   @Override
   public int deleteXOrderItemsByOrderItemIds( Long[] orderItemIds )
   {
      return xOrderItemsMapper.deleteXOrderItemsByOrderItemIds( orderItemIds );
   }

   /**
    * 删除订单明细信息
    *
    * @param orderItemId 订单明细主键
    * @return 结果
    */
   @Override
   public int deleteXOrderItemsByOrderItemId( Long orderItemId )
   {
      return xOrderItemsMapper.deleteXOrderItemsByOrderItemId( orderItemId );
   }
}
