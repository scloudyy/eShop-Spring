package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ShopExecution;
import com.scloudyy.springbackend.entity.Area;
import com.scloudyy.springbackend.entity.PersonInfo;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.entity.ShopCategory;
import com.scloudyy.springbackend.enums.ShopStateEnum;
import com.scloudyy.springbackend.exceptions.ShopOperationException;
import com.scloudyy.springbackend.util.ImageUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void testAddShop() throws ShopOperationException, FileNotFoundException {
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
        shop.setShopName("test add shop3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        File shopImg = new File("/Users/scloudyy/Desktop/1.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), inputStream);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }

    @Test
    public void testModifyShop() throws FileNotFoundException {
        for (long id = 1; id <= 25; id++) {
            Shop shop = shopService.getShopById(id);
            if (shop != null) {
                System.out.println("shopName: " + shop.getShopName());
                File shopImg = new File("/Users/scloudyy/Desktop/1.jpg");
                InputStream inputStream = new FileInputStream(shopImg);
                ImageHolder imageHolder = new ImageHolder(shopImg.getName(), inputStream);
                ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
                assertEquals(shopExecution.getState(), ShopStateEnum.SUCCESS.getState());
                System.out.println("image is: " + shopExecution.getShop().getShopImg());
            } else {
                System.out.println("this shop does't exist");
            }

        }
    }
}
