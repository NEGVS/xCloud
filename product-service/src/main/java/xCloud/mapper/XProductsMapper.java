package xCloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xCloud.entity.XProducts;

@Mapper
public interface XProductsMapper
{
   @Select( "select * from x_products where product_id = #{id}" )
   public XProducts selectProductById(String id);
}
