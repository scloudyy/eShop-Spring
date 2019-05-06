package com.scloudyy.springbackend.service;

import com.scloudyy.springbackend.dto.WechatAuthExecution;
import com.scloudyy.springbackend.entity.WechatAuth;
import com.scloudyy.springbackend.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的微信帐号
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 注册本平台的微信帐号
	 * 
	 * @param wechatAuth
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
