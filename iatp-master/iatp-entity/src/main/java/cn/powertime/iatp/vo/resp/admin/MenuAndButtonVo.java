package cn.powertime.iatp.vo.resp.admin;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MenuAndButtonVo implements Serializable {

    private List<MenuTreeVo> mnues;

    private Map<String, ButtonVo> buttons;

}
