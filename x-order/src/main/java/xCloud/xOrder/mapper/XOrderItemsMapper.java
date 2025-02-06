package xCloud.xOrder.mapper;


import xCloud.xOrder.entity.XOrderItems;

import java.util.List;

/**
 * 订单明细Mapper接口
 * 
 * @author AndyFan
 * @date 2024-11-28
 */
public interface XOrderItemsMapper 
{
    /**
     * 查询订单明细
     * 
     * @param orderItemId 订单明细主键
     * @return 订单明细
     */
    public XOrderItems selectXOrderItemsByOrderItemId(Long orderItemId);

    /**
     * 查询订单明细列表
     * 
     * @param xOrderItems 订单明细
     * @return 订单明细集合
     */
    public List<XOrderItems> selectXOrderItemsList(XOrderItems xOrderItems);

    /**
     * 新增订单明细
     * 
     * @param xOrderItems 订单明细
     * @return 结果
     */
    public int insertXOrderItems(XOrderItems xOrderItems);

    /**
     * 修改订单明细
     * 
     * @param xOrderItems 订单明细
     * @return 结果
     */
    public int updateXOrderItems(XOrderItems xOrderItems);

    /**
     * 删除订单明细
     * 
     * @param orderItemId 订单明细主键
     * @return 结果
     */
    public int deleteXOrderItemsByOrderItemId(Long orderItemId);

    /**
     * 批量删除订单明细
     * 
     * @param orderItemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXOrderItemsByOrderItemIds(Long[] orderItemIds);

    /**
     * 批量插入订单明细
     * @param itemsList 订单明细数据
     * @return 插入数量
     */
    int insertXOrderItemsBatch(List<XOrderItems> itemsList);
}
