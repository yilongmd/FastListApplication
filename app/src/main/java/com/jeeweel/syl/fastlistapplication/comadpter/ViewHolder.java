package com.jeeweel.syl.fastlistapplication.comadpter;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeeweel.syl.fastlistapplication.api.core.ylpublic.OUtils;
import com.jeeweel.syl.fastlistapplication.api.core.ylpublic.StrUtils;


public class ViewHolder
{
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position)
	{
		if (convertView == null)
		{
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView()
	{
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if (view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public int getPosition()
	{
		return mPosition;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text)
	{
		TextView view = getView(viewId);
		if (OUtils.IsNotNull(view)) {
			if (StrUtils.IsNotEmpty(text)) {
				view.setText(text);
			}
		} else {
			Log.d("setText","(ViewID Not not found)没有获取到View ID:" + viewId + " Text:" + text);
		}
		return this;
	}
	
	/**
	 * 获取ImageView
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ImageView getImageView(int viewId)
	{
		ImageView view = getView(viewId);
		return view;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId)
	{
		ImageView view = getView(viewId);
		if (OUtils.IsNotNull(view)) {
			try {
				view.setImageResource(drawableId);
			} catch (Exception e) {
				Log.e("setImageResource",e.getMessage());
			}
		} else {
			Log.e("setImageResource","(ViewID Not not found)没有获取到View ID:" + viewId + " drawableId:" + drawableId);
		}

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm)
	{
		ImageView view = getView(viewId);
		if (OUtils.IsNotNull(view)) {
			try {
				view.setImageBitmap(bm);
			} catch (Exception e) {
				Log.e("setImageBitmap",e.getMessage());
			}
		} else {
			Log.e("setImageBitmap","(ViewID Not not found)没有获取到View ID:" + viewId);
		}
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url)
	{
		ImageView view = getView(viewId);
		if (OUtils.IsNotNull(view)) {
			try {
				ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(url,view);
			} catch (Exception e) {
				Log.e("setImageByUrl",e.getMessage());
			}
		} else {
			Log.e("setImageByUrl","(ViewID Not not found)没有获取到View ID:" + viewId);
		}
		return this;
	}
}
