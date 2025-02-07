package xCloud.xProduct.service;


import xCloud.xProduct.entity.XSites;

import java.util.List;


/**
 * 站点Service接口
 *
 * @author AndyFan
 * @date 2024-11-28
 */
public interface IXSitesService
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
    * 新增站点
    *
    * @param xSites 站点
    * @return 结果
    */
   public int insertXGenerate( XSites xSites );

   /**
    * 修改站点
    *
    * @param xSites 站点
    * @return 结果
    */
   public int updateXSites( XSites xSites );

   /**
    * 批量删除站点
    *
    * @param siteIds 需要删除的站点主键集合
    * @return 结果
    */
   public int deleteXSitesBySiteIds( Long[] siteIds );

   /**
    * 删除站点信息
    *
    * @param siteId 站点主键
    * @return 结果
    */
   public int deleteXSitesBySiteId( Long siteId );
}
