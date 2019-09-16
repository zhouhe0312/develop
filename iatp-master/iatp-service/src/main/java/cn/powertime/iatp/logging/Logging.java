package cn.powertime.iatp.logging;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logging {

    /**
     * 日志类型
     * @return
     */
    EnumLogType type() default EnumLogType.ADD;

    /** 日志信息编码，对应资源文件中定义的日志信息。 */
    String code();

    /**
     * 日志信息变量，对应日志信息中的变量，指定为参数对象名或参数对象的属性名。
     */
    String[] vars() default "";
}
