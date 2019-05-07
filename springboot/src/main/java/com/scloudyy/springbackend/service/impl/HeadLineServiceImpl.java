package com.scloudyy.springbackend.service.impl;

import java.util.List;

import com.scloudyy.springbackend.dao.HeadLineDao;
import com.scloudyy.springbackend.entity.HeadLine;
import com.scloudyy.springbackend.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HeadLineServiceImpl implements HeadLineService {
	private final HeadLineDao headLineDao;

	private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

	@Autowired
	public HeadLineServiceImpl(HeadLineDao headLineDao) {
		this.headLineDao = headLineDao;
	}

	@Override
	@Transactional
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
		return headLineDao.queryHeadLine(headLineCondition);
	}
}
