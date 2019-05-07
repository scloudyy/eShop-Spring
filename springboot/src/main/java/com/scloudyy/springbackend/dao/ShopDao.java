package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao {

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有：店铺名(模糊),店铺状态，店铺类别，区域Id,owner
     *
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
