package xCloud.api.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import xCloud.constant.UserConstants;
import xCloud.entity.BaseEntity;

import javax.validation.constraints.Size;

/**
 * 字典数据表 sys_dict_data
 *
 * @author AndyFan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysDictData extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 字典编码 */
   @Excel(name = "字典编码")
   private Long dictCode;

   /** 字典排序 */
   @Excel(name = "字典排序")
   private Long dictSort;

   /** 字典标签 */
   @Excel(name = "字典标签")
   private String dictLabel;

   /** 字典键值 */
   @Excel(name = "字典键值")
   private String dictValue;

   /** 字典类型 */
   @Excel(name = "字典类型")
   private String dictType;

   /** 样式属性（其他样式扩展） */
   private String cssClass;

   /** 表格字典样式 */
   private String listClass;

   /** 是否默认（Y是 N否）, readConverterExp = "Y=是,N=否" */
   @Excel(name = "是否默认")
   private String isDefault;

   /** 状态（0正常 1停用）, readConverterExp = "0=正常,1=停用" */
   @Excel(name = "状态")
   private String status;

   @NotBlank(message = "字典标签不能为空")
   @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
   public String getDictLabel()
   {
      return dictLabel;
   }

   @NotBlank(message = "字典键值不能为空")
   @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
   public String getDictValue()
   {
      return dictValue;
   }

   @NotBlank(message = "字典类型不能为空")
   @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
   public String getDictType()
   {
      return dictType;
   }

   @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
   public String getCssClass()
   {
      return cssClass;
   }

   public boolean getDefault()
   {
      return UserConstants.YES.equals( this.isDefault );
   }

}
