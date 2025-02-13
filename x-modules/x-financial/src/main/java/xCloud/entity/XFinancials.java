package xCloud.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务对象 x_financials
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XFinancials extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 财务ID */
   private Long financialId;

   /** 商家ID */
   @Excel(name = "商家ID")
   private Long merchantId;

   /** 财务金额 */
   @Excel(name = "财务金额")
   private BigDecimal amount;

   /** 交易类型：'Revenue', 'Expense' */
   @Excel(name = "交易类型：'Revenue', 'Expense'")
   private Long transactionType;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date updatedTime;

}
