package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals(areaList.get(0).getAreaName(), "下城");
    }
}
