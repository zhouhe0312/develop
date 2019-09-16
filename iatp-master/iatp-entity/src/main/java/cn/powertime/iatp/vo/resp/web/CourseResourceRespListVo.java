package cn.powertime.iatp.vo.resp.web;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CourseResourceRespListVo implements Serializable {

    private List<CourseResourceListVo> unitTest;

    private List<CourseResourceListVo> resourceList;

}
