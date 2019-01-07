package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ProductExecution;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.exceptions.ProductOperationException;

import java.util.List;

public interface ProductService {
    /**
     * 修改商品信息以及图片处理
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException;

    Product getProduct(long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException;
}
