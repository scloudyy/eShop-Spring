package com.scloudyy.springbackend.dto;

import com.scloudyy.springbackend.entity.ProductCategory;
import com.scloudyy.springbackend.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {}

    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
    }

    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> list) {
        this.setProductCategoryList(list);
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
