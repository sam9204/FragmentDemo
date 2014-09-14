package com.example.fragmentdemo.fragment;

import java.util.List;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.fragmentdemo.ConstantValue;
import com.example.fragmentdemo.GlobalParams;
import com.example.fragmentdemo.ImageCallback;
import com.example.fragmentdemo.R;
import com.example.fragmentdemo.UIManager;
import com.example.fragmentdemo.adapter.SoftcacheAdapter;
import com.example.fragmentdemo.bean.Hot;
import com.example.fragmentdemo.bean.Params;
import com.example.fragmentdemo.bean.Slice;
import com.example.fragmentdemo.util.NetWorkTask;
import com.example.fragmentdemo.util.PromptManager;

/**
 * 首页
 * @author Administrator
 *
 */
public class HomeFragment extends BaseFragment{

	private GridView gridviewMovie;
	private TextView recommendMoreMovie;
	private SoftcacheAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.il_home, container, false);// container:LayoutParameter参数传递；attachToRoot：false不会挂载到root上
		gridviewMovie = ((GridView) view.findViewById(R.id.gridviewMovie));
		recommendMoreMovie = ((TextView) view.findViewById(R.id.recommendMoreMovie));
		
		adapter = new SoftcacheAdapter(getActivity(), 6);
		gridviewMovie.setAdapter(adapter);
		
		adapter.setCallback(new ImageCallback() {
			
			@Override
			public void imageLoaded(Bitmap bitmap, Object tag) {
				ImageView imageView = (ImageView) gridviewMovie.findViewWithTag(tag);

				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		});
		
		recommendMoreMovie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("data", ConstantValue.URI+ ConstantValue.VIDEO_URI);
				UIManager.getInstance().changeFragment(new MoreFragment(),true, bundle);
			}
		});
		return view;
	}

	@Override
	public void onDestroy() {
		GlobalParams.IMGCACHE.clear();
		super.onDestroy();
	}

	@Override
	public void onStart() {
		// 发送请求，读取Json信息
		// 发送的参数：
		// ①OnResultListener实现类的对象
		// ②URL
		// ③Map<String,String> params;
		// ④是否弹出滚动条
		Params params = new Params();
		params.listener = this;
		params.url = ConstantValue.URI + ConstantValue.SLICE_URI;
		
		new NetWorkTask().executeProxy(params);
		
		super.onStart();
	}

	/**
	 * 根据返回结果做相应处理
	 */
	@Override
	public void onGetResult(Object result, int iError) {
		if (iError == ConstantValue.SUCCESS && result != null) {
			String json = result.toString();
			
			try {
				JSONObject jsonObject = new JSONObject(json);
				List<Slice> parseArray = JSON.parseArray(
						jsonObject.getString("slices"), Slice.class);
				Slice slice = parseArray.get(0);
				List<Hot> hot = slice.getHot();
				
				// 更新界面
				adapter.setHotList(hot);
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			// getActivity() 当前系统仅有的Activity的引用
			PromptManager.showErrorDialog(getActivity(), "服务器忙……");
		}
	}
	
}
