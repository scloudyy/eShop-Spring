package com.scloudyy.springbackend.dto;

import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    private int state;
    // information of state
    private String stateInfo;
    // number of returned shops
    private int count;
    // executed shop(used in add, delete, update)
    private Shop shop;
    // list of shop(used in query)
    private List<Shop> shopList;

    public ShopExecution() {}

    // constructor used when shop execution failed
    public ShopExecution(ShopStateEnum stateEnum) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
    }

    // constructor used when shop execution success
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
        this.setShop(shop);
    }

    // constructor used when shop execution success
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.setState(stateEnum.getState());
        this.setStateInfo(stateEnum.getStateInfo());
        this.setShopList(shopList);
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
