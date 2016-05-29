package com.qlu.android.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlu.android.product.dao.ProductMapper;
import com.qlu.android.product.pojo.Product;
import com.qlu.android.product.service.ProductService;


/**
 * @author: yang
 * 
 */

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public Product selectProduct(String mid) {
		// TODO Auto-generated method stub
		Product product = productMapper.selectProduct(mid);
		System.out.println("product:"+product);
		return product;
	}
	
	
	

}

