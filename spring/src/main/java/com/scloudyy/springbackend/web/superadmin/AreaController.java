package com.scloudyy.springbackend.web.superadmin;

import com.scloudyy.springbackend.entity.Area;
import com.scloudyy.springbackend.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
    private final Logger logger = LoggerFactory.getLogger(AreaController.class);
    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/arealist")
    @ResponseBody
    public Map<String, Object> listArea() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<>();
        List<Area> listArea = null;
        try {
            listArea = areaService.getAreaList();
            modelMap.put("rows", listArea);
            modelMap.put("total", listArea.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;
    }
}