1.在res下定义一个xml文件夹,在下定义一个searchable.xml
<?xml version="1.0" encoding="utf-8"?>
<searchable xmlns:android="http://schemas.android.com/apk/res/android"
    android:label="@string/sms_search"
    android:hint="@string/sms_search" 
    android:searchSuggestAuthority="cn.itcast.smsmanager.MySuggestionProvider"
    android:searchSuggestSelection=" ?" >
</searchable>
2.再定义一个SearchableActivity用来显示查询结果,清单文件如下配置
<activity android:name=".SearchableActivity">
           <intent-filter>
               <action android:name="android.intent.action.SEARCH" />
           </intent-filter>
           <meta-data android:name="android.app.searchable"
                   android:resource="@xml/searchable"/>
        </activity>
public class SearchableActivity extends ListActivity {
	private ListView mListView;	
	private QueryHandler mQueryHandler;	
	private SearchAdapter mAdapter;	
	private final static String[] SMS_PROJECTION = new String[]{Sms._ID,Sms.ADDRESS,Sms.DATE,Sms.BODY};
	private final static int ID_COLUMN_INDEX = 0;
	private final static int ADDRESS_COLUMN_INDEX = 1;
	private final static int DATE_COLUMN_INDEX = 2;
	private final static int BODY_COLUMN_INDEX = 3;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Intent intent = getIntent();	    
	    mListView = getListView();	    
	    mQueryHandler = new QueryHandler(getContentResolver());	    
	    mAdapter = new SearchAdapter(this, null);	    
	    mListView.setAdapter(mAdapter);	    
	    mListView.setBackgroundColor(Color.WHITE);	  
	3.匹配搜索动作,作对应的操作.
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      //自己的搜索操作
	      doMySearch(query);	      
	      Log.i("i", " query " + query);
	    }	  	    
	    mListView.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	private final class MyOnItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Cursor cursor = (Cursor) mAdapter.getItem(position);
			String idStr = cursor.getString(ID_COLUMN_INDEX);		
			Intent intent = new Intent(getApplicationContext(),SmsDetailActivity.class);
			intent.putExtra("_id", idStr);
			startActivity(intent);
		}
		
	}

	/**
	 * 执行搜索功能
	 * @param query
	 */
	private void doMySearch(String query) {
		Uri uri = Sms.CONTENT_URI;
		// select * from table where body like '%love%'
		String selection = Sms.BODY + " like '%" + query + "%'";
		mQueryHandler.startQuery(0, null, uri, SMS_PROJECTION, selection, null, Sms.DATE + " desc");
	};

	private final class SearchViews{
		ImageView header;
		TextView tv_name;
		TextView tv_date;
		TextView tv_body;
	}
	
	private final class SearchAdapter extends CursorAdapter{
		
		private LayoutInflater mInflater;
		
		private long firstSecondOfToday;

		public SearchAdapter(Context context, Cursor c) {
			super(context, c);
			mInflater = LayoutInflater.from(context);
			Time time = new Time();
			time.setToNow();
			time.hour = 0;
			time.minute = 0;
			time.second = 0;
			
			firstSecondOfToday = time.toMillis(false);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.conversation_item, parent, false);
			
			SearchViews views = new SearchViews();
			views.header = (ImageView) view.findViewById(R.id.header);
			views.tv_name = (TextView) view.findViewById(R.id.tv_name);
			views.tv_date = (TextView) view.findViewById(R.id.tv_date);
			views.tv_body = (TextView) view.findViewById(R.id.tv_body);
			
			view.setTag(views);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// TODO Auto-generated method stub
			SearchViews views = (SearchViews) view.getTag();
			String address = cursor.getString(ADDRESS_COLUMN_INDEX);
			long date = cursor.getLong(DATE_COLUMN_INDEX);
			String body = cursor.getString(BODY_COLUMN_INDEX);
			//根据电话号码查询联系人
			String name = null;
			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
			Cursor contact_cursor = getContentResolver().query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
			if(contact_cursor.moveToFirst()){
				name = contact_cursor.getString(0);
			}
			contact_cursor.close();
			if(name != null){
				views.header.setImageResource(R.drawable.ic_contact_picture);
				views.tv_name.setText(name);
			}else{
				views.header.setImageResource(R.drawable.ic_unknown_picture_normal);
				views.tv_name.setText(address);
			}
			
			String dateStr = null;
			if((date - firstSecondOfToday > 0) && (date - firstSecondOfToday < DateUtils.DAY_IN_MILLIS)){
				//show time
				dateStr = DateFormat.getTimeFormat(context).format(date);
			}else{
				// show date
				dateStr = DateFormat.getDateFormat(context).format(date);
			}
			
			views.tv_date.setText(dateStr);
			
			views.tv_body.setText(body);
		}
	}
	
	
	private final class QueryHandler extends AsyncQueryHandler{

		public QueryHandler(ContentResolver cr) {
			super(cr);
		}
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			// TODO Auto-generated method stub
			super.onQueryComplete(token, cookie, cursor);
			
			mAdapter.changeCursor(cursor);
			
			initTitle();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Cursor cursor = mAdapter.getCursor();
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
	}

	/**
	 * 初始化标题
	 */
	public void initTitle() {
		// TODO Auto-generated method stub
		int count = mAdapter.getCount();
		setTitle("查询的结果记录有" + count + "条");
	}
}
4.搜索时下面的提示信息的定义: 清单文件如下:
<provider android:name=".MySuggestionProvider" android:authorities="cn.itcast.smsmanager.MySuggestionProvider"></provider>
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "cn.itcast.smsmanager.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    
    private final static String[] sms_projection = new String[]{Sms._ID,Sms.ADDRESS,Sms.BODY};
    
    private final static String[] columnNames = new String[]{BaseColumns._ID,
    	SearchManager.SUGGEST_COLUMN_TEXT_1,
    	SearchManager.SUGGEST_COLUMN_TEXT_2,
    	SearchManager.SUGGEST_COLUMN_QUERY};

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
    		String[] selectionArgs, String sortOrder) {
    	// TODO Auto-generated method stub
    	Log.i("i", "query  ...");
    	
    	if(selectionArgs != null){
        	String query = selectionArgs[0];
        	
        	if(TextUtils.isEmpty(query)){
        		return null;
        	}
        	
        	Uri uri1 = Sms.CONTENT_URI;
        	String where = Sms.BODY + " like '%" + query + "%'";
        	Cursor cursor = getContext().getContentResolver().query(uri1, sms_projection, where, null, Sms.DATE + " desc ");
        	return changeCursor(cursor);//封装数据
    	}
        return null;
    }
    
    
    private Cursor changeCursor(Cursor cursor){
    	MatrixCursor result = new MatrixCursor(columnNames);
    	if(cursor != null){
    		while(cursor.moveToNext()){
    			Object[] columnValues = new Object[]{cursor.getString(cursor.getColumnIndex(Sms._ID)),
    					cursor.getString(cursor.getColumnIndex(Sms.ADDRESS)),
    					cursor.getString(cursor.getColumnIndex(Sms.BODY)),
    					cursor.getString(cursor.getColumnIndex(Sms.BODY))};
    			result.addRow(columnValues);
    		}
    	}
    	return result;
    }
}
5.最后在menifest中Application中定义哪个SearchActivity去执行查询结果的显示.
 <!-- 指定我们要激活的是哪个SearchableActivity -->
        <meta-data android:name="android.app.default_searchable"
             android:value=".SearchableActivity" />

