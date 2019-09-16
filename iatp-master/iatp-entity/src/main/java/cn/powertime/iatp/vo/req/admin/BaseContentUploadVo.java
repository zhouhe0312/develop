package cn.powertime.iatp.vo.req.admin;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseContentUploadVo extends BaseRowModel implements Serializable {

    /**
     * 序号
     */
    @ExcelProperty(index = 0)
    private Integer id;

    /**
     * 关联问题序号
     */
    @ExcelProperty(index = 1)
    private String depend;

    /**
     * 章节编号
     */
    @ExcelProperty(index = 2)
    private String no;

    /**
     * 章节名称
     */
    @ExcelProperty(index = 3)
    private String name;

    /**
     * 控制措施要求（审计依据原文）
     */
    @ExcelProperty(index = 4)
    private String content;

    /**
     * 控制措施名称
     */
    @ExcelProperty(index = 5)
    private String demain;

    /**
     * 审计评价标准及方法
     */
    @ExcelProperty(index = 6)
    private String question;

    /**
     * 管理/技术
     */
    @ExcelProperty(index = 7)
    private String type;

    /**
     * 收集审计证据
     */
    @ExcelProperty(index = 8)
    private String note;

    /**
     * 审计发现问题列表
     */
    @ExcelProperty(index = 9)
    private String problem;

    /**
     * 风险描述
     */
    @ExcelProperty(index = 10)
    private String rish;

    /**
     * 风险等级
     */
    @ExcelProperty(index = 11)
    private String rishLevel;

    /**
     * 建议
     */
    @ExcelProperty(index = 12)
    private String suggest;

    /**
     * 控制措施责任主体（审计对象）
     */
    @ExcelProperty(index = 13)
    private String auditUnit;

    /**
     * 答题人
     */
    @ExcelProperty(index = 14)
    private String anwser;

    /**
     * 答题人编码
     */
    @ExcelProperty(index = 15)
    private Integer anwserRoler;

}
