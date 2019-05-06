package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.entity.ProductCategory;
import com.scloudyy.springbackend.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Test
    @Ignore
    public void testInsertProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);

        Product product1 = new Product();
        product1.setProductName("回环测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("回环测试2");
        product2.setProductDesc("测试Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        Product product3 = new Product();
        product3.setProductName("circle test3");
        product3.setProductDesc("测试Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(pc1);

        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testQueryProductById() {
        Product product = productDao.queryProductById(1L);
        System.out.println(product.getProductName());
    }

    @Test
    @Ignore
    public void testUpdateProduct() {
        Product product = new Product();
        Shop shop = new Shop();
        product.setProductId(1L);
        shop.setShopId(1L);
        product.setShop(shop);
        product.setProductDesc("hello");
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryProductList() {
        Product productCondition = new Product();
        productCondition.setProductName("测试");
        List<Product> productList = productDao.queryProductList(productCondition, 0, 5);
        System.out.println("List size of QueryProductList is " + productList.size());
        int count = productDao.queryProductCount(productCondition);
        assertEquals(count, productList.size());
    }
}
