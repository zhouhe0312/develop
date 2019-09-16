package cn.powertime.iatp.authserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yang xin
 */
@Configuration
@ConfigurationProperties(prefix = "userlock")
public class UserLockConfig {

    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}
