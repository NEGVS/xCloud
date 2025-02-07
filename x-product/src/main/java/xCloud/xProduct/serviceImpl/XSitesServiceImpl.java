package xCloud.xProduct.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xCloud.util.CodeX;
import xCloud.xProduct.entity.XSites;
import xCloud.xProduct.mapper.XSitesMapper;
import xCloud.xProduct.service.IXSitesService;

import java.util.Date;
import java.util.List;

/**
 * 站点Service业务层处理
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Service
public class XSitesServiceImpl implements IXSitesService
{
   @Autowired
   private XSitesMapper xSitesMapper;

   /**
    * 查询站点
    *
    * @param siteId 站点主键
    * @return 站点
    */
   @Override
   public XSites selectXSitesBySiteId( Long siteId )
   {
      return xSitesMapper.selectXSitesBySiteId( siteId );
   }

   /**
    * 查询站点列表
    *
    * @param xSites 站点
    * @return 站点
    */
   @Override
   public List< XSites > selectXSitesList( XSites xSites )
   {
      return xSitesMapper.selectXSitesList( xSites );
   }

   /**
    * 新增站点
    *
    * @param xSites 站点
    * @return 结果
    */
   @Override
   public int insertXSites( XSites xSites )
   {
      xSites.setCreatedTime( new Date() );
      return xSitesMapper.insertXSites( xSites );
   }

   /**
    * 新增站点--生成
    * @param xSites 商品
    * @return 结果
    */
   @Override
   public int insertXGenerate( XSites xSites )
   {
      int count = 0;
      for ( int i = 0; i < 100; i++ )
      {
         xSites.setName( CodeX.generalComplexRandomString() );
         xSites.setLocation( CodeX.generalComplexRandomString( 15 ) );
         xSites.setMerchantId( 1L );
         xSites.setCreatedTime( new Date() );
         count += xSitesMapper.insertXSites( xSites );
      }
      return count;
   }

   /**
    * 修改站点
    *
    * @param xSites 站点
    * @return 结果
    */
   @Override
   public int updateXSites( XSites xSites )
   {
      return xSitesMapper.updateXSites( xSites );
   }

   /**
    * 批量删除站点
    *
    * @param siteIds 需要删除的站点主键
    * @return 结果
    */
   @Override
   public int deleteXSitesBySiteIds( Long[] siteIds )
   {
      return xSitesMapper.deleteXSitesBySiteIds( siteIds );
   }

   /**
    * 删除站点信息
    *
    * @param siteId 站点主键
    * @return 结果
    */
   @Override
   public int deleteXSitesBySiteId( Long siteId )
   {
      return xSitesMapper.deleteXSitesBySiteId( siteId );
   }
}
