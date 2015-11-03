package com.jeeweel.syl.fastlistapplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeeweel.syl.fastlistapplication.api.core.ylpublic.IntUtils;
import com.jeeweel.syl.fastlistapplication.api.core.ylpublic.OUtils;
import com.jeeweel.syl.fastlistapplication.api.core.ylpublic.StrUtils;
import com.jeeweel.syl.fastlistapplication.comadpter.CommonAdapter;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import java.util.List;

public class JwListActivity extends Activity {
    private PullToRefreshListView mListView; //listView
    private PullToRefreshBase.Mode listViewMode; //刷新模式
    private CommonAdapter commonAdapter; //适配器
    private int pageSize = 20; //一个显示多少个
    private int pageIndex = 1; //当前页
   //默认page
    private int defPage = 1;
    private String sPageSizeName = "pageSize";  //一个显示多少个名称
    private String sPageIndexName = "pageIndex"; //当前页名称
    private String sUrl = ""; //API URL 地址
    private String sParamsStr = ""; //传的URL 参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_jw_list);
       // initListViewController();
    }


    /**
     * 初始化ListView控件
     */
    protected void initListViewController() {
        //获取listview控件
        mListView = (PullToRefreshListView)findViewById(R.id.listview);
        //如果模式为空,那么下拉刷新上拉刷新都支持
        if (OUtils.IsNotNull(getListViewMode())) {
          mListView.setMode(getListViewMode());
        } else {
            mListView.setMode(PullToRefreshBase.Mode.BOTH);
        }
        //加载子类适配器
        mListView.setAdapter(getCommonAdapter());
        //设置刷新监听器
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            // 下拉Pulling Down
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉的时候数据重置
//                new FinishRefresh(JwListActivity.this, 0).execute();
                onListViewHeadRefresh();
            }

            // 上拉Pulling Up
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉的时候添加选项
                onListViewFooterRefresh();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点第一项时默认为1,需-1 才能与数据源匹配
                onListItemClick(position - 1);
//                ToastShow(String.valueOf(position));
            }
        });
        onListViewHeadRefresh();
    }

    protected void onListViewHeadRefresh() {
        // 下拉的时候数据重置
        new FinishRefresh(JwListActivity.this, 0).execute();
    }

    protected void onListViewFooterRefresh() {
        // 下拉的时候数据重置
        new FinishRefresh(JwListActivity.this, 1).execute();
    }

//    public void onListViewRefrsh(){
//        //第一次打开启动加载动画
////        showLoading();
//        new FinishRefresh(JwListActivity.this, 0).execute();
//    }
    /**
     * 用来异步加载数据
     */
    private class FinishRefresh extends AsyncTask<Void, Void, String> {
        private Context context;
        private int mode = 0;//刷新的方式 0 为下拉 1 为上拉

        /**
         * @param context 上下文
         * @param mode  刷新模式
         */
        public FinishRefresh(Context context, int mode) {
            this.context = context;
            this.mode = mode;
        }

        @Override
        protected String doInBackground(Void... params) {
            //获取URL参数
            String paramsStr = getsParams();
            //初始化URL
            String apiStr;
            if (mode == 0) {
                //如果为下拉刷新页码为1
                pageIndex = getDefPage();
                //传入分页字段 默认为pageSize  pageIndex
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(sPageIndexName, IntUtils.toStr(pageIndex));
                ajaxParams.put(sPageSizeName, IntUtils.toStr(pageSize));
                //获取字符串拼装
                paramsStr = ajaxParams.getParamString() +"&"+paramsStr;
                //考虑是否添加key
                apiStr = getsUrl()+paramsStr;
            } else {
                //上拉刷新页码为+1
                pageIndex += 1;
                AjaxParams ajaxParams = new AjaxParams();
                //传入分页字段 默认为pageSize  pageIndex
                ajaxParams.put(sPageIndexName, IntUtils.toStr(pageIndex));
                ajaxParams.put(sPageSizeName, IntUtils.toStr(pageSize));
                //获取字符串拼装
                paramsStr = ajaxParams.getParamString() +"&"+paramsStr;
                //考虑是否添加key
                apiStr = getsUrl()+paramsStr;
            }
            //调试用
            Log.d("apiStr",apiStr);
            FinalHttp http = new FinalHttp();
            String sJson = StrUtils.ObjIfNull(http.getSync(apiStr));
            //JSON处理...
            return sJson;
        }

        @Override
        protected void onPostExecute(String result) {
            //固定的返回格式,如果需要上传到github 那么直接讲result开放给dataSourceLoad
            //返回消息
            if (StrUtils.IsNotEmpty(result)) {
                //子类数据源加载
                dataSourceLoad(mode, result);
            } else {
                //没有获取到数据就退回原来的页码
                --pageIndex;
            }
            //刷新收起
            mListView.onRefreshComplete();
            //通知适配器刷新
            commonAdapter.notifyDataSetChanged();
            //如果有动画则隐藏
//            hideLoading();
        }
    }


    /**
     * 数据源加载
     * @param mode 下拉或者上拉刷新
     * @param sJson Json字符串
     */
    protected void dataSourceLoad(int mode ,String sJson) {

    }


    /**
     * 下拉刷新第一页清空数据源专用
     * @param mode
     * @param sourceList
     */
    public void dataSourceClear(int mode ,List<?> sourceList) {
        //如果是下拉刷新先清除数据
        if (mode == 0) {
            if (sourceList.size() > 0) {
                sourceList.clear();
            }
        }
    }


    /**
     * ListItem被点击
     * @param position
     */
    protected void onListItemClick(int position) {

    }


    /**
     * main list view
     * 主列表获取
     * @return
     */
    public PullToRefreshListView getmListView() {
        return mListView;
    }


    /**
     * 获取列表刷新模式
     * @return
     */
    public PullToRefreshBase.Mode getListViewMode() {
        return listViewMode;
    }

    public void setListViewMode(PullToRefreshBase.Mode listViewMode) {
        this.listViewMode = listViewMode;
    }

    /**
     * 获取适配器
     * @return
     */
    public CommonAdapter getCommonAdapter() {
        return commonAdapter;
    }

    /**
     * 子类设置适配器
     * @param commonAdapter
     */
    public void setCommonAdapter(CommonAdapter commonAdapter) {
        this.commonAdapter = commonAdapter;
    }


    /**
     * 获取子类设置的URL 字符串
     * @return
     */
    public String getsUrl() {
        return sUrl;
    }

    /**
     * 子类设置的URL 字符串
     * @param sUrl
     */
    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    /**
     * 一页显示的个数
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置一页显示的个数
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 页码
     * @return
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置页码
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取额外URL参数
     * @return
     */
    public String getsParams() {
        return sParamsStr;
    }

    /**
     * 子类设置额外URL参数
     * @param sParamsStr
     */
    public void setsParams(String sParamsStr) {
        this.sParamsStr = sParamsStr;
    }

    public int getDefPage() {
        return defPage;
    }

    public void setDefPage(int defPage) {
        this.defPage = defPage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_jw_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
