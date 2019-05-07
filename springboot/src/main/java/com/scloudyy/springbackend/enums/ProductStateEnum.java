package com.scloudyy.springbackend.enums;

public enum ProductStateEnum {
    OFFLINE(-1, "非法商品"), DOWN(0, "下架"), SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(-1002, "商品为空");

    private int state;
    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo) {
        this.setState(state);
        this.setStateInfo(stateInfo);
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public ProductStateEnum stateOf(int state) {
        for (ProductStateEnum ps : values()) {
            if (ps.getState() == state) {
                return ps;
            }
        }
        return null;
    }
}
