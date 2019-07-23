package com.qxy.service;

import com.qxy.pojo.User;

//定义中立的第三方接口
public interface DubboUserService {
	/**
	 * 
	 * 
	 */
	void saveUser(User user);
//单点登录
	String doLogin(User user);
	
}	
