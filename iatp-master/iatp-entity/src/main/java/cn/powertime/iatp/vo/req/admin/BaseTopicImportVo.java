package cn.powertime.iatp.vo.req.admin;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.google.common.collect.Sets;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

@Data
public class BaseTopicImportVo extends BaseRowModel implements Serializable {


    /**
     * 试题题干
     */
    @ExcelProperty(index = 0)
    private String topicStem;

    /**
     * 试题答案
     */
    @ExcelProperty(index = 1)
    private String topicAnswer;

    /**
     * A选项
     */
    @ExcelProperty(index = 2)
    private String optionA;

    /**
     * B选项
     */
    @ExcelProperty(index = 3)
    private String optionB;

    /**
     * C选项
     */
    @ExcelProperty(index = 4)
    private String optionC;

    /**
     * D选项
     */
    @ExcelProperty(index = 5)
    private String optionD;

    /**
     * 试题解析
     */
    @ExcelProperty(index = 6)
    private String topicAnalysis;
    /**
     * 默认分值
     */
    @ExcelProperty(index = 7)
    private Integer scoreValue;

    public boolean isEmpty() {
        try {
            Set<Object> set = Sets.newHashSet();
            Class c = this.getClass();
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                Object o = f.get(this);
                set.add(o);
            }
            if (set.size() == 1) {
                return true;
            }
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


}
