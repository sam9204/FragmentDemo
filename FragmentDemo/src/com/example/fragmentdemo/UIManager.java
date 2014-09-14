package com.example.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 中间容器的管理工具
 * @author Administrator
 *
 */
public class UIManager {
	private static UIManager instance = new UIManager();
	private UIManager(){}
	public static UIManager getInstance(){
		return instance;
	}
	
	/**
	 * 界面切换 
	 * @param target  添加的Fragment
	 * @param isAddStack  按返回键是否缓存当前fragment
	 * @param bundle	封装要传递给下一个fragment的数据
	 */
	public void changeFragment(Fragment target, boolean isAddStack, Bundle bundle){
		
		if (bundle != null) {
			target.setArguments(bundle);
		}
		
		FragmentManager manager = GlobalParams.MAIN.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		// 第一参数：中间容器的id，第二个参数：添加的Fragment
		transaction.replace(R.id.ii_middle, target);
		
		//返回键操作  将当前fragment缓存起来
		if (isAddStack) {
			transaction.addToBackStack(null);
		}
		
		transaction.commit();
	}
}
