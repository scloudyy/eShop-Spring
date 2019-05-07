package com.scloudyy.springbackend.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.scloudyy.springbackend.entity.HeadLine;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineDaoTest {
	@Autowired
	private HeadLineDao headLineDao;

	@Test
	public void testQueryArea() {
		List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
		System.out.println("head line size is " + headLineList.size());
	}
}
