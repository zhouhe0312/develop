package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ZYW
 * @date 2018/5/3
 */
@ApiModel(description = "分页对象")
public class PageVo implements Serializable {
    @ApiModelProperty(value="当前页",name="current",example="1")
    private Integer current;
    @ApiModelProperty(value="每页条数",name="size",example="10")
    private Integer size;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public PageVo() {
    }

    public PageVo(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }
}
