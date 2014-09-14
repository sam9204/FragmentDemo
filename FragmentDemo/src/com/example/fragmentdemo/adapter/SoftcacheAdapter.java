package com.example.fragmentdemo.adapter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmentdemo.GlobalParams;
import com.example.fragmentdemo.ImageCallback;
import com.example.fragmentdemo.R;
import com.example.fragmentdemo.bean.Hot;
import com.example.fragmentdemo.util.ImageDownload;

public class SoftcacheAdapter extends BaseAdapter {
	private List<Hot> hotList;
	
	private Context context;
	private int size;
	
	private ImageCallback callback;
	
	public SoftcacheAdapter(Context context, int size) {
		super();
		this.context = context;
		this.size = size;
	}
	
	public List<Hot> getHotList() {
		return hotList;
	}
	public void setHotList(List<Hot> hotList) {
		this.hotList = hotList;
	}
	public void setCallback(ImageCallback callback) {
		this.callback = callback;
	}
	
	
	@Override
	public int getCount() {
		return this.size;
	}

	@Override
	public Object getItem(int position) {
		if (hotList != null && hotList.size() == this.size) {
			return hotList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.il_gridview_item, null);

			holder.itemImg = (ImageView) convertView.findViewById(R.id.item_img);
			holder.scord = (TextView) convertView.findViewById(R.id.scoreId);
			holder.title = (TextView) convertView.findViewById(R.id.txt_loading);

			holder.itemImg.setImageResource(R.drawable.id_default);

			convertView.setTag(holder);
			
			if (hotList != null) {
				Hot hot = hotList.get(position);
				if (hot != null) {
					String imgUrl = hot.getImg_url();
					if (StringUtils.isNotBlank(imgUrl)) {
						//tag的指定唯一
						holder.itemImg.setTag(hot.getWorks_id());
						Bitmap bm = GlobalParams.IMGCACHE.get(hot.getWorks_id());
						if (bm != null) {
							holder.itemImg.setImageBitmap(bm);
						}else {
							if (callback != null) {
								new ImageDownload(callback).execute(imgUrl, hot.getWorks_id());
							}
						}
					}
				}
				holder.scord.setText(hot.getRating() + "分");
				holder.title.setText(hot.getTitle());
			}
			
		}
		return convertView;
	}
	
	class ViewHolder {
		ImageView itemImg;
		TextView scord;
		TextView title;
	}
	
	@Override
	public void notifyDataSetChanged() {
		if (hotList != null) {
			super.notifyDataSetChanged();
		} else {
			System.out.println("hotList is null");
		}
	}

}
