package com.qlu.android.product.service;

import com.qlu.android.product.pojo.Product;


/**
 * @author: yang
 * 
 */

public interface ProductService {
	
	/**
	 * 查询商品信息
	 */
	public Product selectProduct(String mid);

}

