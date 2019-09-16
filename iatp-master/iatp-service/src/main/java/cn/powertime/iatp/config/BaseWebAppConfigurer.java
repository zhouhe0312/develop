package cn.powertime.iatp.config;


import cn.powertime.iatp.logging.MessageSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @author ZYW
 */
@Configuration
public class BaseWebAppConfigurer implements WebMvcConfigurer {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new BaseInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/file/**","/filePreview/**");
    }

    @Bean
    public MessageSource messageSource(){
        MessageSource messageSource = new MessageSource();
        messageSource.setBasenames("classpath*:/config/*messages.xml");
        return messageSource;
    }

    /**
     * Jackson序列化Long类型时转换成String类型
     * @param converters {@link HttpMessageConverter} 类集合
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .serializers(new ToStringSerializer(Long.TYPE)
                        ,new ToStringSerializer(Long.class)
                        ,new ToStringSerializer(BigInteger.class)
                        ,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)))
                .timeZone(TimeZone.getDefault())
                .build();

        converters.add(0,new MappingJackson2HttpMessageConverter(objectMapper));
    }
}
