package com.qlu.android.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlu.android.product.dao.UserMapper;
import com.qlu.android.product.pojo.User;
import com.qlu.android.product.service.UserService;


/**
 * @author: yang
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User login(User user) {
		User u  = userMapper.selectUser(user);
		return u;
	}

}

