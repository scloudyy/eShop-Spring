package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ShopExecution;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.exceptions.ShopOperationException;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    Shop getShopById(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
