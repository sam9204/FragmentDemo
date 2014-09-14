package com.example.fragmentdemo.listener;

/**
 * Fragment 处理处理结果回复的监听 
 * @author Administrator
 *
 */
public interface OnResultListener {
	/**
	 * 结果处理
	 * @param result
	 * @param iError
	 * 			:结果的状态码
	 */
	void onGetResult(Object result, int iError);
}
