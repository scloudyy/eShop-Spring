package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.junit.Assert;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        Assert.assertEquals(3, areaList.size());
    }
}
