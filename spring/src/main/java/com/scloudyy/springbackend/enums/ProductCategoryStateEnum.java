package com.scloudyy.springbackend.enums;

public enum ProductCategoryStateEnum {
    SUCCESS(1, "success"), INNER_ERROR(-1001, "operation failed"),
    EMPTY_LIST(-1002, "empty list");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.setState(state);
        this.setStateInfo(stateInfo);
    }


    public int getState() {
        return state;
    }

    private void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int state) {
        for (ProductCategoryStateEnum s : values()) {
            if (s.getState() == state) {
                return s;
            }
        }
        return null;
    }
}
