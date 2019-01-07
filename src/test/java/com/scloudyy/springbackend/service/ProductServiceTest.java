package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ProductExecution;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testModifyProduct() throws FileNotFoundException {
        Product product = productService.getProduct(1L);
        product.setProductDesc("测试modifyProduct");
        product.setLastEditTime(new Date());
        File thumbnailFile = new File("/Users/scloudyy/Desktop/1.jpg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
        List<ImageHolder> inputImgList = new ArrayList<>();
        thumbnailFile = new File("/Users/scloudyy/Desktop/1.jpg");
        inputStream = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), inputStream);
        inputImgList.add(imageHolder);
        thumbnailFile = new File("/Users/scloudyy/Desktop/2.jpg");
        inputStream = new FileInputStream(thumbnailFile);
        imageHolder = new ImageHolder(thumbnailFile.getName(), inputStream);
        inputImgList.add(imageHolder);
        ProductExecution pe = productService.modifyProduct(product, thumbnail, inputImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
}
