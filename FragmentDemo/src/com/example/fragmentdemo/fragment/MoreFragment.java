package com.example.fragmentdemo.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.example.fragmentdemo.ConstantValue;
import com.example.fragmentdemo.ImageCallback;
import com.example.fragmentdemo.R;
import com.example.fragmentdemo.adapter.LrucacheAdapter;
import com.example.fragmentdemo.bean.Params;
import com.example.fragmentdemo.bean.Video;
import com.example.fragmentdemo.util.ImageCache;
import com.example.fragmentdemo.util.NetWorkTask;
import com.example.fragmentdemo.util.PromptManager;

/**
 * 首页
 * @author Administrator
 *
 */
public class MoreFragment extends BaseFragment{

	private GridView channelGridView;
	private LrucacheAdapter adapter;
	private int currentpage = 0;
	
	private LinearLayout progressLinear;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.il_channel, container, false);// container:LayoutParameter参数传递

		// ；attachToRoot：false不会挂载到root上
		channelGridView = (GridView) view.findViewById(R.id.ii_channle_grid);
		
		progressLinear = (LinearLayout) view.findViewById(R.id.progress_linear);
		progressLinear.setVisibility(View.INVISIBLE);


		adapter = new LrucacheAdapter(getActivity(), new ImageCallback() {

			@Override
			public void imageLoaded(Bitmap bitmap, Object tag) {
				ImageView imageView = (ImageView) channelGridView
						.findViewWithTag(tag);
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		});
		channelGridView.setAdapter(adapter);

		setListener();

		Bundle bundle = getArguments();
		if (bundle != null) {
			String url = bundle.getString("data");
			Params params = new Params();
			params.isShowProgress = true;
			params.listener = this;
			params.url = url;

			new NetWorkTask().executeProxy(params);
		}

		return view;
	}
	
	private void setListener() {
		channelGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 判断GridView的滚动状态
				// 当GridView停止滚动
				// 获取到当前正在显示的最后那个item的position信息
				// 获取到GridView的所有的孩子的总量，集合的size
				// position==size-1,翻页操作处理
				if (scrollState == SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						// 获取下一页的资源
						currentpage++;
						// 提示信息的处理
						progressLinear.setVisibility(View.VISIBLE);
						
						// 设置参数
						Params params=new Params();
						params.isShowProgress=false;
						params.listener=MoreFragment.this;
						
						params.url = "http://app.video.baidu.com/adnativemovie/?beg="
								+ (currentpage * 20)
								+ "&end="
								+ (currentpage * 20 + 20);
						
						new NetWorkTask().executeProxy(params);
						
					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				
			}
		});

	}
	
	public void onGetResult(Object result, int iError) {
		progressLinear.setVisibility(View.INVISIBLE);
		if (iError == ConstantValue.SUCCESS && result != null) {
			String json = result.toString();
			try {
				JSONObject jsonObject = new JSONObject(json);
				List<Video> videos = JSON.parseArray(
						jsonObject.getJSONObject("video_list").getString(
								"videos"), Video.class);
				// 设置Adappter 更新GridView
				List<Video> videos2 = adapter.getVideos();
				if (videos2 != null && videos2.size() > 0) {
					videos2.addAll(videos);
					adapter.setVideos(videos2);
				} else {
					adapter.setVideos(videos);
				}
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			// getActivity() 当前系统仅有的Activity的引用
			PromptManager.showErrorDialog(getActivity(), "服务器忙……");
		}
		super.onGetResult(result, iError);
	}

	@Override
	public void onDestroy() {
		ImageCache.getInstance().clear();
		super.onDestroy();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
}
