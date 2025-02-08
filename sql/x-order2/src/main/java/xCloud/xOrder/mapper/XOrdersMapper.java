package xCloud.xOrder.mapper;

import org.apache.ibatis.annotations.Param;
import xCloud.xOrder.entity.XOrders;

import java.util.List;

/**
 * 订单Mapper接口
 * 
 * @author AndyFan
 * @date 2024-11-28
 */
public interface XOrdersMapper 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public XOrders selectXOrdersByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param xOrders 订单
     * @return 订单集合
     */
    public List<XOrders> selectXOrdersList(XOrders xOrders);

    /**
     * 新增订单
     * 
     * @param xOrders 订单
     * @return 结果
     */
    public int insertXOrders(XOrders xOrders);

    /**
     * 修改订单
     * 
     * @param xOrders 订单
     * @return 结果
     */
    public int updateXOrders(XOrders xOrders);

    /**
     * 删除订单
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteXOrdersByOrderId(Long orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXOrdersByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 根据支付id查询订单
     * @param paymentId 支付ID
     * @return 订单信息
     */
    XOrders findOrderByPaymentId(@Param("paymentId") String paymentId);
}
