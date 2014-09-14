package com.example.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmentdemo.fragment.HomeFragment;
import com.example.fragmentdemo.fragment.MoreFragment;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ImageView home;
	private ImageView channel;
	private ImageView search;
	private ImageView myself;

	private TextView title;

	private HomeFragment homeFragment;
	private MoreFragment moreFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.il_main);
		GlobalParams.MAIN = this;
		init();
		setListener();
	}

	private void setListener() {
		home.setOnClickListener(this);
		channel.setOnClickListener(this);
		search.setOnClickListener(this);
		myself.setOnClickListener(this);

	}

	private void init() {
		homeFragment = new HomeFragment();
		moreFragment = new MoreFragment();
		initBottom();
		addHome();
	}

	private void addHome() {
		UIManager.getInstance().changeFragment(homeFragment, true, null);
	}

	private void addMore() {
		UIManager.getInstance().changeFragment(moreFragment, true, null);
	}

	private void addHome1() {
		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		// 第一参数：中间容器的id，第二个参数：添加的Fragment
		// transaction.add(R.id.ii_middle, homeFragment);
		transaction.replace(R.id.ii_middle, homeFragment);

		transaction.addToBackStack(null); // 缓存fragment

		// add方法重载
		// transaction.add(arg0, arg1, arg2);第三个参数：tag
		// manager.findFragmentByTag(arg0)

		// replace方法重载
		// transaction.replace(arg0, arg1, arg2);第三个参数：tag
		// manager.findFragmentByTag(arg0)
		// 如果HomeFragment被replace无法进行find
		
		transaction.commit();
	}

	private void addMore1() {
		FragmentManager manager = this.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		// 第一参数：中间容器的id，第二个参数：添加的Fragment
		// transaction.add(R.id.ii_middle, homeFragment);
		transaction.replace(R.id.ii_middle, moreFragment);

		transaction.addToBackStack(null); // 缓存fragment

		transaction.commit();
	}

	private void initBottom() {
		home = (ImageView) findViewById(R.id.ii_bottom_home);
		channel = (ImageView) findViewById(R.id.ii_bottom_channel);
		search = (ImageView) findViewById(R.id.ii_bottom_search);
		myself = (ImageView) findViewById(R.id.ii_bottom_lottery_myself);
		title = (TextView) findViewById(R.id.ii_title_content);

		title.setText("首页");
	}

	@Override
	public void onClick(View v) {
		home.setImageResource(getImageId(0, false));
		channel.setImageResource(getImageId(1, false));
		search.setImageResource(getImageId(2, false));
		myself.setImageResource(getImageId(3, false));

		switch (v.getId()) {
		case R.id.ii_bottom_home:
			title.setText("首页");
			home.setImageResource(getImageId(0, true));
			addHome();
			break;
		case R.id.ii_bottom_channel:
			title.setText("频道");
			channel.setImageResource(getImageId(1, true));
			addMore();
			break;
		case R.id.ii_bottom_search:
			title.setText("本地视频");
			search.setImageResource(getImageId(2, true));
			break;
		case R.id.ii_bottom_lottery_myself:
			title.setText("我的影视大全");
			myself.setImageResource(getImageId(3, true));
			break;
		}
	}

	private int getImageId(int paramInt, boolean paramBoolean) {
		switch (paramInt) {
		default:
			return -1;
		case 0:
			if (paramBoolean)
				return R.drawable.ic_tab_home_press;
			return R.drawable.ic_tab_home;
		case 1:
			if (paramBoolean)
				return R.drawable.ic_tab_channel_press;
			return R.drawable.ic_tab_channel;
		case 2:
			if (paramBoolean)
				return R.drawable.ic_tab_search_press;
			return R.drawable.ic_tab_search;
		case 3:
			if (paramBoolean)
				return R.drawable.ic_tab_my_press;
			return R.drawable.ic_tab_my;
		}

	}

}
