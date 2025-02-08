package xCloud.xProduct.mapper;

import org.apache.ibatis.annotations.Param;
import xCloud.xProduct.entity.XCategories;

import java.util.List;

/**
 * 商品种类Mapper接口
 * 
 * @author AndyFan
 * @date 2024-11-28
 */
public interface XCategoriesMapper 
{
    /**
     * 查询商品种类
     * 
     * @param categoryId 商品种类主键
     * @return 商品种类
     */
    public XCategories selectXCategoriesByCategoryId(Long categoryId);

    /**
     * 查询商品种类列表
     * 
     * @param xCategories 商品种类
     * @return 商品种类集合
     */
    public List<XCategories> selectXCategoriesList(XCategories xCategories);

    /**
     * 新增商品种类
     * 
     * @param xCategories 商品种类
     * @return 结果
     */
    public int insertXCategories(XCategories xCategories);

    /**
     * 修改商品种类
     * 
     * @param xCategories 商品种类
     * @return 结果
     */
    public int updateXCategories(XCategories xCategories);

    /**
     * 删除商品种类
     * 
     * @param categoryId 商品种类主键
     * @return 结果
     */
    public int deleteXCategoriesByCategoryId(Long categoryId);

    /**
     * 批量删除商品种类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXCategoriesByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
}
