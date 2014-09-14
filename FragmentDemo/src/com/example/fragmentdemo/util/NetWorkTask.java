package com.example.fragmentdemo.util;

import android.content.Context;
import android.os.AsyncTask;

import com.example.fragmentdemo.ConstantValue;
import com.example.fragmentdemo.bean.Params;
import com.example.fragmentdemo.fragment.BaseFragment;
import com.example.fragmentdemo.listener.OnResultListener;

public class NetWorkTask extends AsyncTask<Params, Integer, Object> {
	private static final String TAG = "NetWorkTask";
	private OnResultListener onResultListener;
	private Context mContext;
	private int mTag;

	@Override
	protected Object doInBackground(Params... params) {
		HttpClientUtil clientUtil = new HttpClientUtil();
		String result = clientUtil.sendGet(params[0].url);
		return result;
	}

	protected void onPostExecute(Object result) {
		PromptManager.closeProgressDialog();
		if (this.onResultListener != null) {
			int errorCode = ConstantValue.SUCCESS;
			if (result == null) {
				errorCode = ConstantValue.ERROR;
			}

			/**
			 * { try { errorCode = Integer.parseInt(result.toString()); } catch
			 * (Exception e) { } } else {
			 */
			onResultListener
					.onGetResult(errorCode == ConstantValue.ERROR ? null
							: result, errorCode);
		}
	}

	/**
	 * 加强版的开始线程的操作（网络判断）
	 * 
	 * @param params
	 *            <p>
	 *            <li>index=0 的参数为Fragment
	 *            <li>index=1的参数为是否显示滚动条
	 *            <li>index=2链接
	 * @return
	 */
	public final AsyncTask<Params, Integer, Object> executeProxy(
			Params... params) {
		if (params[0].listener instanceof BaseFragment) {
			this.onResultListener = params[0].listener;
			mContext = ((BaseFragment) params[0].listener).getActivity();
			// 判断网络的状态
			if (NetUtil.checkNetType(mContext)) {
				if ((Boolean) params[0].isShowProgress) {
					PromptManager.showProgressDialog(mContext);
				}
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(mContext);
			}
		}
		return null;
	}
}
