package xCloud.xProduct.mapper;

import xCloud.xProduct.entity.XSites;

import java.util.List;

/**
 * 站点Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-07
 */
public interface XSitesMapper
{
   /**
    * 查询站点
    *
    * @param siteId 站点主键
    * @return 站点
    */
   public XSites selectXSitesBySiteId( Long siteId );

   /**
    * 查询站点列表
    *
    * @param xSites 站点
    * @return 站点集合
    */
   public List< XSites > selectXSitesList( XSites xSites );

   /**
    * 新增站点
    *
    * @param xSites 站点
    * @return 结果
    */
   public int insertXSites( XSites xSites );

   /**
    * 修改站点
    *
    * @param xSites 站点
    * @return 结果
    */
   public int updateXSites( XSites xSites );

   /**
    * 删除站点
    *
    * @param siteId 站点主键
    * @return 结果
    */
   public int deleteXSitesBySiteId( Long siteId );

   /**
    * 批量删除站点
    *
    * @param siteIds 需要删除的数据主键集合
    * @return 结果
    */
   public int deleteXSitesBySiteIds( Long[] siteIds );
}

