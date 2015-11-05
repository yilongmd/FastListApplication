# FastListApplication For Android
Pull to refresh the list quickly integrated(上下拉刷新列表快速集成)
+
Common Adapter (快速适配器)
A few lines of code(几行代码即可)

### to release to jcenter, let it can directly from Gradle loading, teach trouble understand, thank you very much!!!
### 想发布到jcenter中,让它可以直接从Gradle加载,麻烦懂的教一下,感激不尽!!!


### extends
extends JwListActivity

### quickly JwListActivity 
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

# 示例图
![image](/githubimages/testdoctor.jpg)

#Do share for the first time, don't understand
#第一次做共享,不大懂
