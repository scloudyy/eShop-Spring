package com.scloudyy.springbackend.service;

import java.util.List;

import com.scloudyy.springbackend.BaseTest;
import com.scloudyy.springbackend.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HeadLineServiceTest extends BaseTest {
	@Autowired
	private HeadLineService headLineService;

	@Test
	public void testGetHeadLineList() {
		List<HeadLine> headLineList = headLineService.getHeadLineList(new HeadLine());
		System.out.println(headLineList.get(0).getLineName());
		HeadLine headLineCondition = new HeadLine();
		headLineCondition.setEnableStatus(1);
		headLineList = headLineService.getHeadLineList(headLineCondition);
		System.out.println(headLineList.get(0).getLineName());
	}
}
