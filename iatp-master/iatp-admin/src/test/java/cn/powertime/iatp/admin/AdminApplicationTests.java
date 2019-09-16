package cn.powertime.iatp.admin;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] a){
        for(int i = 0 ; i < 30; i++){
            System.out.println(IdWorker.getId());
        }
    }

}
