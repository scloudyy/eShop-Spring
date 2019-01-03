package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.Shop;

public interface ShopDao {

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    Shop queryByShopId(long shopId);
}
