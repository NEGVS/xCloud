package xCloud.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息
 *
 * @author AndyFan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysFile
{
   /**
    * 文件名称
    */
   private String name;

   /**
    * 文件地址
    */
   private String url;

}
