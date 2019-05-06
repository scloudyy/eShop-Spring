package com.scloudyy.springbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scloudyy.springbackend.dao.PersonInfoDao;
import com.scloudyy.springbackend.entity.PersonInfo;
import com.scloudyy.springbackend.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	private final PersonInfoDao personInfoDao;

	@Autowired
	public PersonInfoServiceImpl(PersonInfoDao personInfoDao) {
		this.personInfoDao = personInfoDao;
	}

	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}
}
