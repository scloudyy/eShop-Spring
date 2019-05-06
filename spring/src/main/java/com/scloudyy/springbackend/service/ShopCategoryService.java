package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
