package cn.powertime.iatp.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 姓名
     */
    private String username;
    /**
     * 用户类型  1 管理端用户 2 应用端用户'
     */
    private Integer type;

    /**
     * 帐号
     */
    private String acount;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    @TableField(strategy= FieldStrategy.IGNORED)
    private String phone;

    /**
     * 1:男，0:女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码修改日期
     */
    private LocalDateTime modPasswdDate;

    /**
     * 多点登录
     */
    private Integer multipoint;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 所属单位ID
     */
    private Long unitId;

    /**
     * 是否可删除，1：可删除，2：禁止删除
     */
    private Integer del;

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
