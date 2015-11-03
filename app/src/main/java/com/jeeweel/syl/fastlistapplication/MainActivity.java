package com.jeeweel.syl.fastlistapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jeeweel.syl.fastlistapplication.comadpter.CommonAdapter;
import com.jeeweel.syl.fastlistapplication.comadpter.ViewHolder;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends JwListActivity {
    List<MsgItem> mListItems; //dataSource
    CommonAdapter commonAdapter; //commonAdapter 万能适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jw_list);
        mListItems = new ArrayList<MsgItem>();
        initListViewController();
    }

    @Override
    protected void initListViewController() {
        commonAdapter = new CommonAdapter<MsgItem>(this, mListItems, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, MsgItem item) {
                helper.setText(android.R.id.text1, item.getMsg());
            }
        };
        setCommonAdapter(commonAdapter);

        super.initListViewController();
    }

    /**
     * // 下拉Pulling Down
     //Http Get  update mListItems
     */
    @Override
    protected void onListViewHeadRefresh() {
        // 下拉Pulling Down
        //Http Get  update mListItems
        AjaxParams params = new AjaxParams();
        params.put("pageIndex","1");
        params.put("pageSize","10");
        FinalHttp ylHttp = new FinalHttp();
        ylHttp.get("www.xxx.com/getGoodsList?", params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String s) {
                //JSON Add
                MsgItem msgItem = new MsgItem();
                msgItem.setMsg("aaa");
                getmListView().onRefreshComplete();
                //通知适配器刷新
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                getmListView().onRefreshComplete();
                //通知适配器刷新
                commonAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * // 上拉Pulling Up
     //Http Get
     */
    @Override
    protected void onListViewFooterRefresh() {
        // 上拉Pulling Up
        //Http Get
        AjaxParams params = new AjaxParams();
        params.put("pageIndex","2"); // + 1
        params.put("pageSize","10");
        FinalHttp ylHttp = new FinalHttp();
        ylHttp.get("www.xxx.com/getGoodsList?", params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String s) {
                //JSON Add
                MsgItem msgItem = new MsgItem();
                msgItem.setMsg("aaa");
                getmListView().onRefreshComplete();
                //通知适配器刷新
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                getmListView().onRefreshComplete();
                //通知适配器刷新
                commonAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onListItemClick(int position) {
        Toast.makeText(this,"select position:" + position,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
