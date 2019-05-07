package com.scloudyy.springbackend.dto;

import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    private int state;
    private String stateInfo;

    private Product product;
    private List<Product> productList;

    private int count;

    public ProductExecution() {}

    /**
     * used when execution failed
     * @param stateEnum
     */
    public ProductExecution(ProductStateEnum stateEnum) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
    }

    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
        this.setProduct(product);
    }

    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
        this.setProductList(productList);
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
