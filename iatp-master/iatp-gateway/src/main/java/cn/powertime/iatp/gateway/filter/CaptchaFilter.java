package cn.powertime.iatp.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CaptchaFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaFilter.class);


    private static String LOGIN_URL = "/login/oauth/token";
    
    private static String CLIENT_ID ="webapp";


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        if(uri.getPath().contains(LOGIN_URL)){
            HttpHeaders httpHeaders = request.getHeaders();
            String clientIdBase64 = Splitter.on(" ").splitToList(httpHeaders.getFirst("Authorization")).get(1);
            String clientId = Splitter.on(":").splitToList(new String(Base64.getDecoder().decode(clientIdBase64))).get(0);
            if(StringUtils.equals(clientId,CLIENT_ID)){
                String captcha = httpHeaders.getFirst("captcha");
                String captchaKey = request.getHeaders().getFirst("captchaKey");
                ServerHttpResponse response = exchange.getResponse();
                if(StringUtils.isEmpty(captcha)) {
                    return returns(response,4000,"验证码为空");
                }
                String redisCaptcha = stringRedisTemplate.boundValueOps(captchaKey).get();
                if(!StringUtils.equalsIgnoreCase(captcha,redisCaptcha)) {
                    return returns(response,4001,"验证码错误");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange.mutate().request(request).build());
    }


    private Mono<Void> returns(ServerHttpResponse response, Integer code , String msg){
        JSONObject message = new JSONObject();
        message.put("code", code);
        message.put("message", msg);
        message.put("timestamp", System.currentTimeMillis());
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return -201;
    }
}
