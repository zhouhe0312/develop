package cn.powertime.iatp.gateway;

import org.orclight.java.util.captha.CaptchaClient;
import org.orclight.java.util.captha.strategy.SimpleCaptchaStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public CaptchaClient captchaClient(){
        return CaptchaClient.create()
                .captchaStrategy(new SimpleCaptchaStrategy())
                .build();
    }

}
