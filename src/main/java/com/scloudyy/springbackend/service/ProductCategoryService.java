package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getProductCategoryList(long shopId);
}
