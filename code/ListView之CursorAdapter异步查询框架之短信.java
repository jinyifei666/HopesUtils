1.核心就在于不用之前的ListAdapter而用cursorAdapter,再加上一个异步查询类:AsyncQueryHandler
2.这个类可以直接接收cursor也可以在handler查询到结果后用adapter.changeCursor(cursor);把结果传进去.
3.AsyncQueryHandler类在查询到结果后就会有执行onQueryComplete方法.从而可以在这用adapter.changeCursor(cursor);把数据给Adapter
4.CursorAdapter有两个重要抽象方法要重写:newView与bindView,newView用于初始化item里的控件,而bindView接收一个cursor,用于绑定数据给item.
public class ConversationActivity extends Activity{
//定义要插叙的数据库内容.
	private static final String[] PROJECTION = new String[]{
				"sms.thread_id AS _id",
				"sms.body AS snippet",
	//			"COUNT(*) AS msg_count",
				"groups.msg_count AS msg_count",
				"sms.address AS address",
				"sms.date AS date"
		};
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conversation);
		initView();	
		startQuery();		
	}
1.异步查询框架查询
	private void startQuery() {
		Uri uri = Uri.parse("content://sms/conversations");
		/**
		 * token SQL 查询  唯一标示 ID 代表就是当前查询结果的唯一标示
		 * cookie 传递对象 View
		 * uri 指定查询的位置	查询短信会话信息
		 * projection  select name, age  查询的结果（字段）
		 * selection 查询条件  where  SQL where id = ?
		 * selectionArgs	?
		 * orderBy 排序
		 */
		queryHandler.startQuery(0, null, uri, PROJECTION, null, null," date DESC");

	}
	private void initView() {
		bt_new_msg = (Button) findViewById(R.id.bt_new_msg);
		bt_all_selected = (Button) findViewById(R.id.bt_all_selected);
		bt_cancel_selected = (Button) findViewById(R.id.bt_cancel_selected);
		bt_delete = (Button) findViewById(R.id.bt_delete);
		
		//添加点击监听
		bt_new_msg.setOnClickListener(this);
		bt_all_selected.setOnClickListener(this);
		bt_cancel_selected.setOnClickListener(this);
		bt_delete.setOnClickListener(this);
		
		edit = findViewById(R.id.edit);
		listView = (ListView) findViewById(R.id.listView);
		tv_empty = (TextView) findViewById(R.id.tv_empty);
		listView.setEmptyView(tv_empty);
		changeMode(DISPLAYMODE.list);
		queryHandler = new QueryHandler(getContentResolver());
		adapter = new SmsAdapter(this,null);
		listView.setAdapter(adapter);
		
	}
2.QueryHandler异步查询类的定义
	private class QueryHandler extends AsyncQueryHandler {

		public QueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			super.onQueryComplete(token, cookie, cursor);
			//更新数据
			//数据一旦查询成功,就把数据更新至Adapter中
			adapter.changeCursor(cursor);
		}
3.ListView适配器CursorAdapter的定义		
	private class ConversationAdapter extends CursorAdapter{

		private ViewHolder holder;
		private LayoutInflater inflater;
		private long firstSecondOfToday;

		public ConversationAdapter(Context context, Cursor c) {
			super(context, c);	
			inflater = getLayoutInflater().from(context);//得到布局加载器	
		}

		//加载布局 找到控件
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
//			View.inflate(context, resource, root);这个方法获取不到父类的参数
			View view = inflater.inflate(R.layout.conversation_item, parent, false);
			holder = new ViewHolder();
			
			holder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
			holder.header = (ImageView) view.findViewById(R.id.header);
			holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holder.tv_body = (TextView) view.findViewById(R.id.tv_body);
			holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
			
			view.setTag(holder);
			
			return view;
		}

		//数据绑定到控件
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			//找到控件
			holder = (ViewHolder) view.getTag();
			CheckBox checkBox = holder.checkBox;
			ImageView header = holder.header;
			TextView tv_name = holder.tv_name;
			TextView tv_body = holder.tv_body;
			TextView tv_date = holder.tv_date;
			
			//得到数据
			/**
			"sms.thread_id AS thread_id",
			"sms.body AS snippet",
			"COUNT(*) AS msg_count",
			"sms.address AS address",
			"sms.date AS date"
			 */
			String idStr = cursor.getString(0);
			String body = cursor.getString(1);
			int msg_count = cursor.getInt(2);
			String address = cursor.getString(3);
			long date = cursor.getLong(4);
			//设置数据
			tv_date.setText(dateStr);
			tv_body.setText(body);
		}
	}
		private class ViewHolder{
			CheckBox checkBox;
			ImageView header;
			TextView tv_name;
			TextView tv_body;
			TextView tv_date;
		}
	}
}