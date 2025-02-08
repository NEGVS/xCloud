package xCloud.xProduct.mapper;

import org.apache.ibatis.annotations.Param;
import xCloud.xProduct.entity.XProducts;

import java.util.List;

/**
 * 商品Mapper接口
 * 
 * @author AndyFan
 * @date 2024-11-28
 */
public interface XProductsMapper 
{
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    public XProducts selectXProductsByProductId(Long productId);

    /**
     * 查询商品列表
     * 
     * @param xProducts 商品
     * @return 商品集合
     */
    public List<XProducts> selectXProductsList(XProducts xProducts);

    /**
     * 新增商品
     * 
     * @param xProducts 商品
     * @return 结果
     */
    public int insertXProducts(XProducts xProducts);

    /**
     * 修改商品
     * 
     * @param xProducts 商品
     * @return 结果
     */
    public int updateXProducts(XProducts xProducts);
   /**
     * 修改商品-库存
     *
     * @param xProducts 商品
     * @return 结果
     */
    public int updateInventory(XProducts xProducts);

    /**
     * 删除商品
     * 
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteXProductsByProductId(Long productId);

    /**
     * 批量删除商品
     * 
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXProductsByProductIds(@Param("productIds") List<String> productIds);
}
