package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long pid;

    /**
     * 在vue中name表示路由名称，非vue是资源名称
     */
    private String name;

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
     * TreeTable排序
     */
    private String pids;

    /**
     * 1 菜单 2 按钮
     */
    private Integer type;

    private String des;

    /**
     * 状态，1 未删除，0 禁用，-1 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
