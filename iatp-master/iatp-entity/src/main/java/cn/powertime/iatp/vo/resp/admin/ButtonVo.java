package cn.powertime.iatp.vo.resp.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class ButtonVo implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String name;
    private String icon;
    @JsonIgnore
    private String permission;




}
