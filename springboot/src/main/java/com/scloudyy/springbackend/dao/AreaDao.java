package com.scloudyy.springbackend.dao;


import com.scloudyy.springbackend.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {

    List<Area> queryArea();
}
