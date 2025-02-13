package xCloud.service;

import xCloud.entity.XFinancials;

import java.util.List;

/**
 * 财务Service接口
 *
 * @author AndyFan
 * @date 2024-11-28
 */
public interface IXFinancialsService
{
   /**
    * 查询财务
    *
    * @param financialId 财务主键
    * @return 财务
    */
   public XFinancials selectXFinancialsByFinancialId( Long financialId );

   /**
    * 查询财务列表
    *
    * @param xFinancials 财务
    * @return 财务集合
    */
   public List< XFinancials > selectXFinancialsList( XFinancials xFinancials );

   /**
    * 新增财务
    *
    * @param xFinancials 财务
    * @return 结果
    */
   public int insertXFinancials( XFinancials xFinancials );

   /**
    * 新增财务
    *
    * @param xFinancials 财务
    * @return 结果
    */
   public int insertXGenerate( XFinancials xFinancials );

   /**
    * 修改财务
    *
    * @param xFinancials 财务
    * @return 结果
    */
   public int updateXFinancials( XFinancials xFinancials );

   /**
    * 批量删除财务
    *
    * @param financialIds 需要删除的财务主键集合
    * @return 结果
    */
   public int deleteXFinancialsByFinancialIds( Long[] financialIds );

   /**
    * 删除财务信息
    *
    * @param financialId 财务主键
    * @return 结果
    */
   public int deleteXFinancialsByFinancialId( Long financialId );
}
