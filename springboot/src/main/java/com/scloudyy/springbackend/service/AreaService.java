package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.entity.Area;

import java.util.List;

public interface AreaService {
    public static final String AREALISTKEY = "arealist";
    List<Area> getAreaList();
}
