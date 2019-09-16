package cn.powertime.iatp.vo.resp.admin;

import com.alibaba.excel.annotation.ExcelColumnNum;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 日志信息表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLogExportVo extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ExcelProperty(value = { "账号" })
    @ExcelColumnNum(0)
    private String acount;

    @ExcelProperty(value = { "姓名" })
    @ExcelColumnNum(1)
    private String username;

    /**
     * 日志类型（1:增，2:删，3:改，4:查）
     */
    @ExcelProperty(value = { "日志类型" })
    @ExcelColumnNum(2)
    private String type;

    /**
     * 日志说明（日志数据）
     */
    @ExcelProperty(value = { "日志说明" })
    @ExcelColumnNum(3)
    private String descInfo;

    /**
     * 操作详情
     */
    @ExcelProperty(value = { "操作详情" })
    @ExcelColumnNum(4)
    private String remark;

    @ExcelProperty(value = { "执行结果" })
    @ExcelColumnNum(5)
    private String isSuccess;

    /**
     * 创建时间
     */
    @ExcelProperty(value = { "创建时间" })
    @ExcelColumnNum(6)
    private Date createTime;





}
