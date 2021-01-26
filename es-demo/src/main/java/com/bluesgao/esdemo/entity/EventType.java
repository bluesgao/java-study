package com.bluesgao.esdemo.entity;

public enum EventType {

    /**
     * 新增
     */
    INSERT("INSERT", "新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE", "修改"),

    /**
     * 删除
     */
    DELETE("DELETE", "删除");

    private String code;
    private String desc;

    EventType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EventType get(String event) {
        for (EventType item : EventType.values()) {
            if (item.getCode().equalsIgnoreCase(event)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }


}
