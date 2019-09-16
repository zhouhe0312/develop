package cn.powertime.iatp.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 全局熔断器
 * @author ZYW
 */
@RestController
public class FallbackController {

    private final static Map<String,Object> FALLBACK = ImmutableMap.of("code",1001,"message","请求超时！");

    @GetMapping("/fallback")
    public String fallback() {
        return JSONObject.toJSONString(FALLBACK);
    }
}
