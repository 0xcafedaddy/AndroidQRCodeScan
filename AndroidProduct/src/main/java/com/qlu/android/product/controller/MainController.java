package com.qlu.android.product.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qlu.android.product.pojo.Product;
import com.qlu.android.product.pojo.User;
import com.qlu.android.product.service.ProductService;
import com.qlu.android.product.service.UserService;
import com.qlu.android.product.util.Constant;
import com.qlu.android.product.util.JSONUtil;


/**
 * @author: yang
 * 
 */
@RestController
public class MainController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="login", method = RequestMethod.GET)
	public String login(User user,HttpServletRequest request, HttpServletResponse response){
		System.out.println("user:"+user);
		User u = userService.login(user);
		//判断用户是否登录成功
		if(u != null){
			return "{\"login\":0}";
		}else{
			return "{\"login\":1}";
		}
	}
	
	
	@RequestMapping(value="findProduct",method = RequestMethod.GET)
	public String findProduct(String mid,HttpServletRequest request, HttpServletResponse response){
		//N001
		Product product =  productService.selectProduct(mid);
		
		//将这个信息写入session
		//将信息放入到一个容器中
		request.getSession().setAttribute(Constant.PRODUCT_KEY, product);
		
		//相当于跳转到productInfo.jsp页面
		return "productInfo";
	}
	
	
	
}

