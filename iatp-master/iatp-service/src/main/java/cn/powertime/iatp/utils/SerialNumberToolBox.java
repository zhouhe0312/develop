package cn.powertime.iatp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SerialNumberToolBox {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SerialNumberToolBox.redisTemplate = redisTemplate;
    }

    public static String userCodeBySerialNumber(String customerId,String date) {
        Long increment = redisTemplate.opsForValue().increment(customerId+date, 1);
        redisTemplate.expire(customerId+date,1, TimeUnit.DAYS);
        String result = String.valueOf(increment);
        return date + cover(result.length(),result);
    }

    private static String  cover(Integer length,String result){
        switch (length){
            case 1:
                result = "000"+result;
                break;
            case 2:
                result = "00"+result;
                break;
            case 3:
                result = "0"+result;
                break;
            default:
                break;
        }
        return result;
    }

}
