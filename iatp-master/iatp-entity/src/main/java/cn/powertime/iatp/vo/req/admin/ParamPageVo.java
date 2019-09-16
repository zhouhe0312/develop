package cn.powertime.iatp.vo.req.admin;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ZYW
 * @date 2018/5/3
 */
public class ParamPageVo<T> implements Serializable {

    @Valid
    @NotNull(message = "params参数不能为空！")
    private T params;

    private PageVo page;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public PageVo getPage() {
        return page;
    }

    public void setPage(PageVo page) {
        this.page = page;
    }
}
