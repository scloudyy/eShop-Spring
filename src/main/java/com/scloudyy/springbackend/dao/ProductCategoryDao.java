package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryList(long shopId);
}
