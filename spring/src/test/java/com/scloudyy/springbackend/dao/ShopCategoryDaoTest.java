package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println("list size is " + shopCategoryList.size());
    }
}
