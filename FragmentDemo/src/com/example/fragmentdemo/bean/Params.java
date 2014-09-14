package com.example.fragmentdemo.bean;

import java.util.Map;

import com.example.fragmentdemo.listener.OnResultListener;

/**
 * 发送请求携带参数
 * @author Administrator
 *
 */
public class Params {
	// ①OnResultListener实现类的对象
	public OnResultListener listener;
	// ②URL
	public String url;
	// ③请求参数Map<String,String> params;
	public Map<String, String> params;
	// ④是否弹出滚动条
	public boolean isShowProgress = true;
}
