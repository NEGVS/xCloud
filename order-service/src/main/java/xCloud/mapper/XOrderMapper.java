package xCloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xCloud.entity.XOrders;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/1/15 16:20
 *@ClassName XOrderMapper
 */
@Mapper
public interface XOrderMapper
{
   @Select("select * from x_orders where order_id = #{orderId}")
   XOrders selectOrderById( String orderId );
}
