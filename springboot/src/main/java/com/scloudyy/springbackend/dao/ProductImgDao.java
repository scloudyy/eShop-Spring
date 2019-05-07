package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgDao {
    int batchInsertProductImg(List<ProductImg> productImgList);

    List<ProductImg> queryProductImgList(long productId);

    int deleteProductImgByProductId(long productId);
}
