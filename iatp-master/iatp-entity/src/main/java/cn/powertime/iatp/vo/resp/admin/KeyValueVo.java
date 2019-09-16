package cn.powertime.iatp.vo.resp.admin;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZYW
 */
@Data
@Builder
public class KeyValueVo implements Serializable {

    private String key;

    private String value;
}
