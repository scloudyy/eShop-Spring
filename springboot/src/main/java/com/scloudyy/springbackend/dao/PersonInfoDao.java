package com.scloudyy.springbackend.dao;

import com.scloudyy.springbackend.entity.PersonInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonInfoDao {

	/**
	 * 通过用户Id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);

	/**
	 * 添加用户信息
	 * 
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

}
