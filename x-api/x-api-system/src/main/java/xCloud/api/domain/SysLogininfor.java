package xCloud.api.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import xCloud.entity.BaseEntity;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author AndyFan
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysLogininfor extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** ID , cellType = ColumnType.NUMERIC */
   @Excel(name = "序号")
   private Long infoId;

   /** 用户账号 */
   @Excel(name = "用户账号")
   private String userName;

   /** 状态 0成功 1失败 , readConverterExp = "0=成功,1=失败" */
   @Excel(name = "状态")
   private String status;

   /** 地址 */
   @Excel(name = "地址")
   private String ipaddr;

   /** 描述 */
   @Excel(name = "描述")
   private String msg;

   /** 访问时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @Excel(name = "访问时间", width = 30, databaseFormat = "yyyy-MM-dd HH:mm:ss")
   private Date accessTime;

}