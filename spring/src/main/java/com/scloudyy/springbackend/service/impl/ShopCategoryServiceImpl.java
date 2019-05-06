package com.scloudyy.springbackend.service.impl;

import com.scloudyy.springbackend.dao.ShopCategoryDao;
import com.scloudyy.springbackend.entity.ShopCategory;
import com.scloudyy.springbackend.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryDao shopCategoryDao;

    @Autowired
    public ShopCategoryServiceImpl(ShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
