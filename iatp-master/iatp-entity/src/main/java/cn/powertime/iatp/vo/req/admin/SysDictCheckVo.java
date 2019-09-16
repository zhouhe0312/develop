package cn.powertime.iatp.vo.req.admin;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2019-04-17
 * Time: 15:22
 */
public class SysDictCheckVo implements Serializable {

    private Long id;
    @NotBlank(message = "字典类型ID不能为空")
    private String typeId;
    @NotBlank(message = "字典值不能为空")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
