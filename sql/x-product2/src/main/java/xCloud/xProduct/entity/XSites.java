package xCloud.xProduct.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xCloud.entity.BaseEntity;

import java.util.Date;

/**
 * 站点对象 x_sites
 * 
 * @author AndyFan
 * @date 2024-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XSites extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 站点ID */
    private Long siteId;

    /** 站点名称 */
    @Excel(name = "站点名称")
    private String name;

    /** 站点位置 */
    @Excel(name = "站点位置")
    private String location;

    /** 商家ID */
    @Excel(name = "商家ID")
    private Long merchantId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

}
