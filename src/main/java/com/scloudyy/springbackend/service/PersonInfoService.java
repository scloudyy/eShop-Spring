package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 根据用户Id获取personInfo信息
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

}
