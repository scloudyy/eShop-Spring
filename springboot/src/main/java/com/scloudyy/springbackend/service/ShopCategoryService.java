package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
