package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBQueryProductCategoryList() {
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("list size is " + productCategoryList.size());
    }

    @Test
    public void testABatchInsertProductCategory() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("类别1");
        productCategory1.setShopId(1L);
        productCategory1.setPriority(4);
        productCategory1.setCreateTime(new Date());

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("类别2");
        productCategory2.setShopId(1L);
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());

        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory1);
        list.add(productCategory2);

        int effectedNum = productCategoryDao.batchInsertProductCategory(list);
        assertEquals(effectedNum, 2);
    }

    @Test
    public void testCDeleteProductCategory() {
        long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc : productCategoryList) {
            if (pc.getProductCategoryName().equals("类别1") || pc.getProductCategoryName().equals("类别2")) {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(effectedNum, 1);
            }
        }
    }
}
