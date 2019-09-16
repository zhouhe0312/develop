package cn.powertime.iatp.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZYW
 */
@SpringCloudApplication
@ComponentScan(basePackages = "cn.powertime.iatp")
@EnableFeignClients(basePackages = {"cn.powertime.iatp"})
@MapperScan(basePackages = "cn.powertime.iatp.mapper")
@EnableScheduling
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
