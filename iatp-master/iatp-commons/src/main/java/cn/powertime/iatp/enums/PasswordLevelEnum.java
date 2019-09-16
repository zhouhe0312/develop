package cn.powertime.iatp.enums;

import java.util.HashMap;
import java.util.Map;

public enum PasswordLevelEnum {

    VERY_WEAK(0, "非常弱","密码要求：任意字符组成"),
    WEAK(25, "弱","密码要求：不少于4个字符，包含大小写字母"),
    AVERAGE(50, "一般","密码要求：不少于5个字符，包含大小写字母 + 2个及以上数字"),
    STRONG(60, "强","密码要求：不少于8个字符，包含大小写字母 + 2个及以上数字"),
    VERY_STRONG(70, "非常强","密码要求：不少于8个字符，包含大小写字母 + 1个及以上数字 + 1个及以上符号"),
    SECURE(80, "安全","密码要求：不少于8个字符，包含大小写字母 + 2个及以上数字 + 1个及以上符号"),
    VERY_SECURE(90, "非常安全","密码要求：不少于8个字符，包含大小写字母 + 2个及以上数字 + 2个及以上符号"),
    ;

    public Integer level;

    public String notes;

    public String desc;

    PasswordLevelEnum(Integer level, String notes, String desc) {
        this.level = level;
        this.notes = notes;
        this.desc = desc;
    }

    private static Map<Integer, PasswordLevelEnum> map = new HashMap<>();

    static {
        for (PasswordLevelEnum p : PasswordLevelEnum.values()) {
            map.put(p.level, p);
        }
    }

    public static PasswordLevelEnum getNotes(Integer level) {
        return map.get(level);
    }

    public static PasswordLevelEnum getPasswordLevelEnum(Integer level) {
        PasswordLevelEnum resultEnum = null;
        PasswordLevelEnum[] enumArray = PasswordLevelEnum.values();

        for (int i = 0; i < enumArray.length; i++) {
            if (enumArray[i].getLevel()  == level) {
                resultEnum = enumArray[i];
                break;
            }
        }
        return resultEnum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}