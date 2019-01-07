package com.scloudyy.springbackend.service.impl;

import com.scloudyy.springbackend.dao.ProductDao;
import com.scloudyy.springbackend.dao.ProductImgDao;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ProductExecution;
import com.scloudyy.springbackend.entity.Product;
import com.scloudyy.springbackend.entity.ProductImg;
import com.scloudyy.springbackend.enums.ProductStateEnum;
import com.scloudyy.springbackend.exceptions.ProductOperationException;
import com.scloudyy.springbackend.service.ProductService;
import com.scloudyy.springbackend.util.ImageUtil;
import com.scloudyy.springbackend.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductDao productDao;
    private final ProductImgDao productImgDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductImgDao productImgDao) {
        this.productDao = productDao;
        this.productImgDao = productImgDao;
    }

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException {
        logger.debug("entry addProduct");
        if (product != null && product.getShop() != null && product.getShop().getShopId() > 0) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail != null) {
                addProductTumbnail(product, thumbnail);
                logger.debug("add thumbnails");
            }
            try {
                int effectedNum = productDao.insertProduct(product);
                logger.debug("insert product");
                if (effectedNum <= 0) {
                    throw new ProductOperationException("Failed to insert product");
                }
            } catch (Exception e) {
                throw new ProductOperationException(e.getMessage());
            }
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImageList(product, productImgHolderList);
                logger.debug("add image list");
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
        return new ProductExecution(ProductStateEnum.SUCCESS, product);
    }

    private void addProductTumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImageList(Product product, List<ImageHolder> imageHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder imageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImage(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        try {
            int effectedNum = productImgDao.batchInsertProductImg(productImgList);
            if (effectedNum <= 0) {
                throw new ProductOperationException("Failed to insert images");
            }
        } catch (Exception e) {
            throw new ProductOperationException(e.getMessage());
        }
    }
}
