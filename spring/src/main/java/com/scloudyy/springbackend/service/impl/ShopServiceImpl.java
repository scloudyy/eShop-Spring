package com.scloudyy.springbackend.service.impl;

import com.scloudyy.springbackend.dao.ShopDao;
import com.scloudyy.springbackend.dto.ImageHolder;
import com.scloudyy.springbackend.dto.ShopExecution;
import com.scloudyy.springbackend.entity.Shop;
import com.scloudyy.springbackend.enums.ShopStateEnum;
import com.scloudyy.springbackend.exceptions.ShopOperationException;
import com.scloudyy.springbackend.service.ShopService;
import com.scloudyy.springbackend.util.ImageUtil;
import com.scloudyy.springbackend.util.PageCalculator;
import com.scloudyy.springbackend.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopDao shopDao;

    @Autowired
    public ShopServiceImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Transactional  // keep function transactional, must throw RuntimeException then it can be rollback
    @Override
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("Filed to create shop");
            } else {
                if (thumbnail.getImage() != null) {
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error: " + e.getMessage());
                    }

                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("Failed to update shop");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }
         return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    @Override
    public Shop getShopById(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Transactional
    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            if (thumbnail.getImage() != null && thumbnail.getImageName() != null
                    && !thumbnail.getImageName().equals("")) {
                Shop tmpShop = shopDao.queryByShopId(shop.getShopId());
                if (tmpShop.getShopImg() != null) {
                    ImageUtil.deleteFileOrPath(tmpShop.getShopImg());
                }
                addShopImg(shop, thumbnail);
            }
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.updateShop(shop);
            if (effectedNum <= 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            throw new ShopOperationException("modifyShop error: " + e.getMessage());
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        if (shopCondition == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
            shopExecution.setState(ShopStateEnum.SUCCESS.getState());
        } else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }


}
