package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg() {
        // productId为1的商品里添加两个详情图片记录
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testBQueryProductImgList() throws InterruptedException {
        Thread.sleep(2000);  // 主从同步的数据库中，读操作想从数据库请求，但是可能并没有那么快更新，所以insert之后马上query可能会出错
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        assertEquals(2, productImgList.size());
    }

    @Test
    public void testCDeleteProductImgByProductId() {
        int effectNum = productImgDao.deleteProductImgByProductId(1L);
        assertEquals(2, effectNum);
    }
}
