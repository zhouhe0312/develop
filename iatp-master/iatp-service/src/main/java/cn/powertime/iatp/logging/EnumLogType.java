package cn.powertime.iatp.logging;

/**
 * 1:增，2:删，3:改，4:查
 */
public enum EnumLogType {
    ADD(1)
    ,DEL(2)
    ,UPDATE(3)
    ,SEARCH(4);

    private Integer type;

    EnumLogType(Integer type) {
        this.type = type;
    }

    public Integer getType(){
        return this.type;
    }
}
