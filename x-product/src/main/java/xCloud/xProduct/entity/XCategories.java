package xCloud.xProduct.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xCloud.entity.BaseEntity;

import java.util.Date;

/**
 * 商品种类对象 x_categories
 *
 * @author AndyFan
 * @date 2024-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class XCategories extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 商品种类ID */
   private Long categoryId;

   /** 商品种类名称 */
   @Excel(name = "商品种类名称")
   private String name;

   /** 商品种类描述 */
   @Excel(name = "商品种类描述")
   private String description;

   /** 父级商品种类ID */
   @Excel(name = "父级商品种类ID")
   private Long parentCategoryId;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date updatedTime;

}
