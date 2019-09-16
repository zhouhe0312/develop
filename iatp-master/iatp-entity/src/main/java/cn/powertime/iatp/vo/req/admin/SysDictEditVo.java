package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liqi
 * @Description:
 * @date 2019-04-1700416:03
 */
@Data
public class SysDictEditVo implements Serializable {

    @NotNull(message = "字典ID不能为空！")
    private Long id;

    /**
     * 类型ID
     */
    @NotNull(message = "字典类型不能为空！")
    private Long typeId;

    /**
     * 排序
     */
    //private Integer sortNum;

    /**
     * 字典名称
     */
    @NotNull(message = "字典名称不能为空！")
    @Length(max = 20, message = "字典名称不能超过20个字符")
    private String name;

    /**
     * 字典值
     */
    @NotNull(message = "字典值不能为空！")
    @Length(max = 10, message = "字典值不能超过10个字符")
    private String value;

    @Length(max = 50, message = "字典描述不能超过50个字符")
    private String des;

    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
