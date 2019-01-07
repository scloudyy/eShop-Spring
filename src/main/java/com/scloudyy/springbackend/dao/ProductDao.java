package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.Product;

public interface ProductDao {
    int insertProduct(Product product);

    Product queryProductById(long productId);

    int updateProduct(Product productCondition);
}
