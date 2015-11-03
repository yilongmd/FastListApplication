package com.jeeweel.syl.fastlistapplication.comadpter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeeweel.syl.fastlistapplication.R;


public class GridViewAdapter extends BaseAdapter{
	   /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据
     */
    private String[] mData;
    /**
     * 获取布局对象
     */
    private LayoutInflater mInflater;
    /**
     * 缓存控件
     */
    private class ViewHolder {
        private TextView value;
        private ImageView imvlaue;
    }
    private TypedArray imagesArrays;
    private int layoutId;
    /**
     * 构造方法
     * @param context 上下文
     * @param data 数据
     */
    public GridViewAdapter(Context context, String[] data,TypedArray imagesArrays,int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.imagesArrays = imagesArrays;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public String getItem(int position) {
        
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("Recycle")
	@SuppressWarnings("deprecation")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            int x =parent.getHeight();
            convertView = mInflater.inflate(layoutId, null);
            viewHolder.value = (TextView) convertView.findViewById(R.id.mode_tv);
            viewHolder.imvlaue = (ImageView) convertView.findViewById(R.id.mode_im);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.value.setText(getItem(position));
        viewHolder.imvlaue.setBackgroundDrawable(imagesArrays.getDrawable(position));
        return convertView;
    }
    

}
