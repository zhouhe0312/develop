package cn.powertime.iatp.core;

import com.google.common.collect.ImmutableMap;

/**
 * @author ZYW
 */
public final class IatpConstants {
    /**
     * 试卷类型编号名称对映Map
     */
    public static final ImmutableMap<String,String> TEST_TYPE_MAP =
            ImmutableMap.of("1","随堂测验","2","单元测试","3","课程/实验测试","4","综合测试");

    /**
     * 资源打点
     */
    public static final String RECORD_KEY = "record_set";
}
