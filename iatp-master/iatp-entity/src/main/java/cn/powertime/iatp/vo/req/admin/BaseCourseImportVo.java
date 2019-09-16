package cn.powertime.iatp.vo.req.admin;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseCourseImportVo extends BaseRowModel implements Serializable {

    /**
     * 类型，1 课程，2：实验
     */
    @ExcelProperty(index = 0)
    private String type;

    /**
     * 课程（实验）名称
     */
    @ExcelProperty(index = 1)
    private String courseName;

    /**
     * 课程（实验）介绍
     */
    @ExcelProperty(index = 2)
    private String introduce;

}
