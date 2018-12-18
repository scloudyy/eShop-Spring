package com.scloudyy.springbackend.service.impl;

import com.scloudyy.springbackend.dao.AreaDao;
import com.scloudyy.springbackend.entity.Area;
import com.scloudyy.springbackend.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaDao areaDao;

    @Autowired
    public AreaServiceImpl(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
