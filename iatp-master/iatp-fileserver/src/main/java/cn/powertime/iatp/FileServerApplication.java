package cn.powertime.iatp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZYW
 */
@EnableScheduling
@SpringCloudApplication
@ComponentScan(basePackages = "cn.powertime.iatp")
@EnableFeignClients(basePackages = {"cn.powertime.iatp"})
@MapperScan(basePackages = "cn.powertime.iatp.mapper")
public class FileServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServerApplication.class, args);
    }

}
