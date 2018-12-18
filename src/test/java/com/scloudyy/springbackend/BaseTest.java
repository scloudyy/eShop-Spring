package com.scloudyy.springbackend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * 配置spring和junit整合，junit启动时加载springIOC容器
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件的位置
@ContextConfiguration(locations = { "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
    @Test
    public void basicTest(){
        // just avoid error: "No runnable methods"
    }
}
