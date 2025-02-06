package xCloud.xOrder.service;


import xCloud.result.XResponseResult;
import xCloud.xOrder.entity.XOrders;
import xCloud.xOrder.param.OrderParam;
import xCloud.xOrder.param.PayParam;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author AndyFan
 * @date 2024-11-28
 */
public interface IXOrdersService
{
   /**
    * 查询订单
    *
    * @param orderId 订单主键
    * @return 订单
    */
   public XOrders selectXOrdersByOrderId( Long orderId );

   /**
    * 查询订单列表
    *
    * @param xOrders 订单
    * @return 订单集合
    */
   public List< XOrders > selectXOrdersList( XOrders xOrders );

   /**
    * 新增订单
    *
    * @param xOrders 订单
    * @return 结果
    */
   public XResponseResult insertXOrders( XOrders xOrders );

   /**
    * 新增订单
    *
    * @param xOrders 订单
    * @return 结果
    */
   public int insertXGenerate( XOrders xOrders );

   /**
    * 修改订单
    *
    * @param xOrders 订单
    * @return 结果
    */
   public int updateXOrders( XOrders xOrders );

   /**
    * 批量删除订单
    *
    * @param orderIds 需要删除的订单主键集合
    * @return 结果
    */
   public int deleteXOrdersByOrderIds( List<Long> orderIds );

   /**
    * 删除订单信息
    *
    * @param orderId 订单主键
    * @return 结果
    */
   public int deleteXOrdersByOrderId( Long orderId );

   /**
    * 新增订单
    * @param dto 订单参数
    * @return 结果
    */
   XResponseResult addOrder( OrderParam dto);

   /**
    * 支付
    *
    * @param param 支付参数
    * @return 支付结果
    */
   XResponseResult pay( PayParam param);

   /**
    * 订单支付（测绘）
    * @param paymentId 订单唯一标识符（微信、支付宝支付状态可以用这个查询，数据库的状态也可用这个查询）
    * @return 结果
    */
   XResponseResult testPay(String paymentId);
}
