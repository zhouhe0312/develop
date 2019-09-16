package cn.powertime.iatp.enums;

/**
 * 异常Code和消息的对应关系枚举
 * @author ZYW
 */
public enum  ExceptionCodeMsgEnum {

    /**
     * 电信IoT SDK内部状态
     */
    EX_100610("100610","设备未激活"),
    EX_200002("200002","资源冲突，通知类型已经被订阅"),
    /**
     * 抄读设备数据自定义错误码和消息，从200101到200200
     */
    EX_200101("200101","抄读设备数据异常！"),
    EX_200102("200102","设备服务信息为空！"),

    /**
     * 插入读取的数据时发生的异常错误码，从200201到200300
     */
    EX_200201("200201","批量插入历史基础数据失败！"),

    /**
     * 操作设备信息的自定义异常错误码，从200301到200400
     */
    EX_200301("200301","插入设备信息失败！"),
    EX_200302("200302","更新设备信息异常！"),
    EX_200303("200303","订阅回调地址前缀为空！"),
    EX_200304("200304","订阅通知失败！"),
    EX_200305("200305","异常上报的设备没有找到！"),

    /**
     * 设备数据操作异常，错误码从200401到200500
     */
    EX_200401("200401","查询到的设备数据为空！"),
    EX_200402("200402","保存燃气表信息失败！"),
    EX_200403("200403","更新燃气表信息失败！"),
    EX_200404("200404","保存水表信息失败！"),
    EX_200405("200405","更新水表信息失败！"),
    EX_200406("200406","设备执行命令失败！"),
    EX_200407("200407","执行的命令类型错误！"),
    EX_200408("200408","设备设置了不下发单价！"),
    EX_200409("200409","保存命令执行信息失败！"),
    EX_200410("200410","下发充值命令失败！"),
    EX_200411("200411","设备命令回调处理器为空！"),
    EX_200412("200412","下发调价命令失败！"),
    EX_200413("200413","下发开关命令失败！"),

    /**
     * 设备订单
     */
    EX_200501("200501","保存缴费订单失败！"),

    /**
     * 报表统计异常错误码从300101到300200
     */
    FORM_300101("300101","用户身份证号不能为空！"),
    FORM_300102("300102","收费项目ID不能为空！"),
    FORM_300103("300103","统计类型不能为空1:按日统计 2:按月统计 3:按年统计！"),

    /**
     * 移动端异常错误码是以4开头的错误码
     */
    EX_400101("400101","短信验证码类型错误！"),
    EX_400102("400102","两次密码输入不一致！"),
    EX_400103("400103","用户已存在！"),
    EX_400104("400104","绑定的仪表不存在！"),
    EX_400105("400105","仪表不存在！"),
    EX_400106("400106","发送短信接口对象为空！"),
    EX_400107("400107","设备不能重复绑定！"),
    EX_400108("400108","数据没有查到！"),
    EX_400109("400109","传入的参数为空！"),
    EX_400110("400110","部分数据没有查到！")
    ;


    ExceptionCodeMsgEnum(String code,String message) {
        this.code = code;
        this.message = message;
    }
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
