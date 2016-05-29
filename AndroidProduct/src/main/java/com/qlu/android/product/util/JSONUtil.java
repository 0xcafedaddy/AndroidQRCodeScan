package com.qlu.android.product.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	
	public static String getJson(String method,int i){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(method, i);
		String jsonString = JSONObject.toJSONString(map).toString();
		return jsonString;
	}
	public static String getJson(String method,boolean i){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(method, i);
		String jsonString = jsonObject.toString();
		return jsonString;
	}
	public static String getJson(List list){
		Map<String,Object> map = new HashMap<String,Object>();
		String jsonString = JSONObject.toJSONString(map).toString();
		return jsonString;
	}
	public static String getListJson(String method,List list){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(method, list);
		String jsonString = jsonObject.toString();
		return jsonString;
	}
	public static String getJson(String method,Job job){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(method, job);
		String jsonString = jsonObject.toString();
		return jsonString;
	}
}
