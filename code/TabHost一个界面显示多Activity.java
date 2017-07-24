1.TabHost一个界面显示多Activity
TabHost是整个Tab的容器，包括两部分，TabWidget和FrameLayout。TabWidget就是每个tab的标签，FrameLayout则是tab内容。

1、如果我们使用extends TabAcitivty，如同ListActivity，TabHost必须设置为@android:id/tabhost 
2、TabWidget必须设置android:id为@android:id/tabs 
3、FrameLayout需要设置android:id为@android:id/tabcontent 
4.Activity要继承TabActivity
//定义xml
<?xml version="1.0" encoding="utf-8"?>
<TabHost xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@android:id/tabhost"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" >

    <RelativeLayout
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >

        <TabWidget
            android:layout_alignParentBottom="true"
            android:id="@android:id/tabs"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content" >
        </TabWidget>

        <FrameLayout 
	        android:id="@android:id/tabcontent"
	        android:layout_width="fill_parent"
	        android:layout_height="0dip"
	        android:layout_weight="1"//这样表示剩余空间都归这个控件
	        >
	    </FrameLayout>
    </RelativeLayout>

</TabHost>
//定义Activity
public class SystemOptActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_opt);
		
		TabHost tabHost = getTabHost();//获取到最外层的容器.
		TabSpec  tabSpec1 = tabHost.newTabSpec("缓存清理");//里面的String表示这个TabSpec的标记
		tabSpec1.setIndicator(getTabView(R.drawable.tab1,"缓存清理"));
		tabSpec1.setContent(new Intent(this,CleanCacheActivity.class));
		
		tabHost.addTab(tabSpec1);
		
		TabSpec  tabSpec2 = tabHost.newTabSpec("sd卡清理");
		tabSpec2.setIndicator(getTabView(R.drawable.tab2,"sd卡清理"));
		tabSpec2.setContent(new Intent(this,CleanSDActivity.class));
		
		tabHost.addTab(tabSpec2);
		
		TabSpec  tabSpec3 = tabHost.newTabSpec("启动项清理");
		tabSpec3.setIndicator(getTabView(R.drawable.tab3,"启动项清理"));
		tabSpec3.setContent(new Intent(this,CleanStartupActivity.class));
		
		tabHost.addTab(tabSpec3);
	}
	
	private View getTabView(int icon,String text){
		View view = View.inflate(this, R.layout.tab_system_opt, null);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_tab);
		TextView tv = (TextView)view.findViewById(R.id.tv_tab);
		iv.setImageResource(icon);
		tv.setText(text);
		return view;
	}
	//这个表示Tab改变监听: 这里定义一个SharedPreference去保存id,那么可以在每次打开应用时,是打开对应的tab
	
	private class MyOnTabChangeListener implements OnTabChangeListener{

		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			int currentTab = tabHost.getCurrentTab();
			Editor editor = sp.edit();
			editor.putInt("tabId", currentTab);
			editor.commit();
		}
		
	}
	//添加监听
	 tabHost.setOnTabChangedListener(new MyOnTabChangeListener());
        
        int tabId = sp.getInt("tabId", -1);
        
        if(tabId == -1){
        	tabHost.setCurrentTab(0);
        } else {
        	tabHost.setCurrentTab(tabId);
        }
}