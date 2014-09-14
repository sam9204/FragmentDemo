package com.example.fragmentdemo;

import com.example.fragmentdemo.util.SoftValueMap;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

public class GlobalParams {
	/**
	 * Activity引用
	 */
	public static FragmentActivity MAIN;
	
	/**
	 * wap的ip信息
	 */
	public static String PROXY_IP = "";
	/**
	 * wap的端口信息
	 */
	public static int PROXY_PORT = 0;
	
	/**
	 * 存放少量图片的软引用的集合
	 */
	public static SoftValueMap<Object, Bitmap> IMGCACHE = new SoftValueMap<Object, Bitmap>();
}
