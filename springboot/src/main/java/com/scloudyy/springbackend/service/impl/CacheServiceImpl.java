package com.scloudyy.springbackend.service.impl;

import java.util.Set;

import com.scloudyy.springbackend.cache.JedisUtil;
import com.scloudyy.springbackend.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	private JedisUtil.Keys jedisKeys;

	@Override
	public void removeFromCache(String keyPrefix) {
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for (String key : keySet) {
			jedisKeys.del(key);
		}
	}

}
