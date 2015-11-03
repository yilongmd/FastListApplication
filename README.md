# FastListApplication
Pull to refresh the list quickly integrated(上下拉刷新列表快速集成)

### extends
extends JwListActivity

### quickly JwListActivity 
### 比如我们可以在多行文本框里输入一段代码,来一个Java版本的HelloWorld吧  
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

     /**
     * init ListView Controller
     * 初始化ListViewController
     */
    @Override
    protected void initListViewController() {
        commonAdapter = new CommonAdapter<MsgItem>(this, mListItems, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder helper, MsgItem item) {
                helper.setText(android.R.id.text1, item.getMsg());
            }
        };
        setCommonAdapter(commonAdapter);

        //init
        super.initListViewController();
    }

    /**
     * 下拉Pulling Down
     * Http Get  update mListItems
     */
    @Override
    protected void onListViewHeadRefresh() {
        // 下拉Pulling Down
        //Http Get  update mListItems
       
    }

    /**
     * 上拉Pulling Up
     * Http Get
     */
    @Override
    protected void onListViewFooterRefresh() {
        // 上拉Pulling Up
        //Http Get
        
    }
}


