package xCloud.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xCloud.entity.XFinancials;
import xCloud.mapper.XFinancialsMapper;
import xCloud.service.IXFinancialsService;
import xCloud.util.CodeX;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 财务Service业务层处理
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@Service
public class XFinancialsServiceImpl implements IXFinancialsService
{
   @Autowired
   private XFinancialsMapper xFinancialsMapper;

   /**
    * 查询财务
    *
    * @param financialId 财务主键
    * @return 财务
    */
   @Override
   public XFinancials selectXFinancialsByFinancialId( Long financialId )
   {
      return xFinancialsMapper.selectXFinancialsByFinancialId( financialId );
   }

   /**
    * 查询财务列表
    *
    * @param xFinancials 财务
    * @return 财务
    */
   @Override
   public List< XFinancials > selectXFinancialsList( XFinancials xFinancials )
   {
      return xFinancialsMapper.selectXFinancialsList( xFinancials );
   }

   /**
    * 新增财务
    *
    * @param xFinancials 财务
    * @return 结果
    */
   @Override
   public int insertXFinancials( XFinancials xFinancials )
   {
      xFinancials.setCreatedTime( new Date() );
      return xFinancialsMapper.insertXFinancials( xFinancials );
   }

   /**
    * 新增财务--生成
    *
    * @param xFinancials x
    * @return 结果
    */
   @Override
   public int insertXGenerate( XFinancials xFinancials )
   {
      int count = 0;
      for ( int i = 0; i < 100; i++ )
      {
         xFinancials.setTransactionType( 1L );
         xFinancials.setMerchantId( 1L );
         xFinancials.setAmount( new BigDecimal( CodeX.generalRandomInt() ) );
         xFinancials.setCreatedTime( new Date() );
         count += xFinancialsMapper.insertXFinancials( xFinancials );
      }
      return count;
   }

   /**
    * 修改财务
    *
    * @param xFinancials 财务
    * @return 结果
    */
   @Override
   public int updateXFinancials( XFinancials xFinancials )
   {
      return xFinancialsMapper.updateXFinancials( xFinancials );
   }

   /**
    * 批量删除财务
    *
    * @param financialIds 需要删除的财务主键
    * @return 结果
    */
   @Override
   public int deleteXFinancialsByFinancialIds( Long[] financialIds )
   {
      return xFinancialsMapper.deleteXFinancialsByFinancialIds( financialIds );
   }

   /**
    * 删除财务信息
    *
    * @param financialId 财务主键
    * @return 结果
    */
   @Override
   public int deleteXFinancialsByFinancialId( Long financialId )
   {
      return xFinancialsMapper.deleteXFinancialsByFinancialId( financialId );
   }
}
