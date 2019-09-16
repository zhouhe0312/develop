package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-22
 * Time: 19:08
 */
@Data
public class SysResEditVo implements Serializable {

    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "菜单父ID不能为空")
    private Long pid;

    @NotBlank(message = "菜单名称不能为空")
    @Length(max = 10, message = "权限名称不能超过10个字符")
    private String name;

    @NotBlank(message = "菜单编码不能为空")
    @Length(max = 10, message = "权限编码不能超过10个字符")
    private String permission;

    /**
     * vue中表示路由的path
     */
    private String url;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * attach(隶属字段)1:管理端，2:应用端，3：移动端
     */
    @NotNull(message = "菜单隶属不能为空，默认传1")
    private Integer attach;

    /**
     * PC菜单图片
     */
    private String icon1;

    /**
     * APP菜单图片
     */
    private String icon2;
    /**
     * 1 菜单 2 按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @Length(max = 200, message = "权限描述不能超过200个字符")
    private String des;

    @NotNull(message = "菜单状态不能为空")
    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
