package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件信息表主键
     */
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 源文件名称
     */
    private String origName;

    /**
     * 后缀名
     */
    private String ext;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 备注
     */
    private String des;

    /**
     * 1:正常，0：删除
     */
    private Integer status;

    /**
     * 客户id
     */
    private Long customerId;

    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    private String createUser;

    private LocalDateTime updateTime;

    /**
     * 修改用户
     */
    private String updateUser;


}
