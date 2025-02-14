package xCloud.api.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import xCloud.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author AndyFan
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysUser extends BaseEntity
{
   private static final long serialVersionUID = 1L;

   /** 用户ID , type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "用户编号" */
   @Excel(name = "用户序号")
   private Long userId;

   /** 部门ID , type = Type.IMPORT */
   @Excel(name = "部门编号")
   private Long deptId;

   /** 用户账号 */
   @Excel(name = "登录名称")
   private String userName;

   /** 用户昵称 */
   @Excel(name = "用户名称")
   private String nickName;

   /** 用户邮箱 */
   @Excel(name = "用户邮箱")
   private String email;

   /** 手机号码 , cellType = ColumnType.TEXT */
   @Excel(name = "手机号码")
   private String phonenumber;

   /** 用户性别 , readConverterExp = "0=男,1=女,2=未知" */
   @Excel(name = "用户性别")
   private String sex;

   /** 用户头像 */
   private String avatar;

   /** 密码 */
   private String password;

   /** 帐号状态（0正常 1停用） , readConverterExp = "0=正常,1=停用" */
   @Excel(name = "帐号状态")
   private String status;

   /** 删除标志（0代表存在 2代表删除） */
   private String delFlag;

   /** 最后登录IP , type = Type.EXPORT */
   @Excel(name = "最后登录IP")
   private String loginIp;

   /** 最后登录时间 */
   @Excel(name = "最后登录时间", width = 30, databaseFormat = "yyyy-MM-dd HH:mm:ss")
   private Date loginDate;

   /** 部门对象 , targetAttr = "leader", type = Type.EXPORT */
   //    @Excels({
   //        @Excel(name = "部门名称"),
   //        @Excel(name = "部门负责人")
   //    })
   @Excel(name = "部门负责人")
   private SysDept dept;

   /** 角色对象 */
   private List< SysRole > roles;

   /** 角色组 */
   private Long[] roleIds;

   /** 岗位组 */
   private Long[] postIds;

   /** 角色ID */
   private Long roleId;

   public boolean isAdmin()
   {
      return isAdmin( this.userId );
   }

   public static boolean isAdmin( Long userId )
   {
      return userId != null && 1L == userId;
   }

   //   @Xss(message = "用户昵称不能包含脚本字符")
   @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
   public String getNickName()
   {
      return nickName;
   }

   //   @Xss(message = "用户账号不能包含脚本字符")
   @NotBlank(message = "用户账号不能为空")
   @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
   public String getUserName()
   {
      return userName;
   }

   @Email(message = "邮箱格式不正确")
   @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
   public String getEmail()
   {
      return email;
   }

   @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
   public String getPhonenumber()
   {
      return phonenumber;
   }

}
