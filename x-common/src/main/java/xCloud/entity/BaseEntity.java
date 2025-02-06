package xCloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity基类
 *
 * @author AndyFan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable
{
   private static final long serialVersionUID = 1L;

   /** 搜索值 */
   @JsonIgnore
   private List< String > idList;
   /** 搜索值 */
   @JsonIgnore
   private String searchValue;

   /** 创建者 */
   @TableField(exist = false)
   private String createBy;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date createdTime;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date updatedTime;

   /** 创建时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @TableField(exist = false)
   private Date createTime;

   /** 更新者 */
   @TableField(exist = false)
   private String updateBy;

   /** 更新时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @TableField(exist = false)
   private Date updateTime;

   /** 备注 */
   @TableField(exist = false)
   private String remark;

   @TableField(exist = false)
   private String pageNo;

   @TableField(exist = false)
   private String pageNum;

   @TableField(exist = false)
   private String pageSize;

   @TableField(exist = false)
   private String[] dateRange;

   @TableField(exist = false)
   private Date startDate;

   @TableField(exist = false)
   private Date endDate;

   /** 请求参数 */
   @JsonInclude(JsonInclude.Include.NON_EMPTY)
   @TableField(exist = false)
   private Map< String, Object > params = new HashMap<>();

}
