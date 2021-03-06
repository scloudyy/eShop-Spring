package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.Area;
import com.scloudyy.springbackend.entity.PersonInfo;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("Shop for test");
        shop.setShopDesc("test Desc");
        shop.setShopAddr("test Addr");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("Checking");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("test for update");
        shop.setShopAddr("test for update");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testQueryByShopId() {
        Shop shop = shopDao.queryByShopId(1L);
        System.out.println("areaId: " + shop.getArea().getAreaId());
        System.out.println("areaName: " + shop.getArea().getAreaName());
        System.out.println("categoryId: " + shop.getShopCategory().getShopCategoryId());
        System.out.println("categoryName: " + shop.getShopCategory().getShopCategoryName());
    }

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小：" + shopList.size());
        System.out.println("店铺总数：" + count);
    }
}
