package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryServiceTest extends BaseTest {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void testGetShopCategoryList() {
        List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
        System.out.println("Size of list is " + shopCategoryList.size());
    }
}
