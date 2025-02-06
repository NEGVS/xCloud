package xCloud.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *@Description
 *@Author Andy Fan
 *@Date 2025/2/6 10:45
 *@ClassName User
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User
{
   private static final long serialVersionUID = 1L;

   /** 用户ID */
   private Long userId;

   /** 部门ID */
   @Excel(name = "部门ID")
   private Long deptId;

   /** 用户账号 */
   @Excel(name = "用户账号")
   private String userName;

   /** 用户昵称 */
   @Excel(name = "用户昵称")
   private String nickName;

   /** 用户类型（00系统用户） , readConverterExp = "0=0系统用户"*/
   @Excel(name = "用户类型")
   private String userType;

   /** 用户邮箱 */
   @Excel(name = "用户邮箱")
   private String email;

   /** 手机号码 */
   @Excel(name = "手机号码")
   private String phonenumber;

   /** 用户性别（0男 1女 2未知） , readConverterExp = "0=男,1=女,2=未知"*/
   @Excel(name = "用户性别")
   private String sex;

   /** 头像地址 */
   @Excel(name = "头像地址")
   private String avatar;

   /** 密码 */
   @Excel(name = "密码")
   private String password;

   /** 帐号状态（0正常 1停用） , readConverterExp = "0=正常,1=停用"*/
   @Excel(name = "帐号状态")
   private String status;

   /** 删除标志（0代表存在 2代表删除） */
   private String delFlag;

   /** 最后登录IP */
   @Excel(name = "最后登录IP")
   private String loginIp;

   /** 最后登录时间 */
   @JsonFormat(pattern = "yyyy-MM-dd")
   @Excel(name = "最后登录时间", width = 30, databaseFormat = "yyyy-MM-dd")
   private Date loginDate;

}
