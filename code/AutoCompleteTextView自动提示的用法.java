/*AutoCompleteTextView 搭配CursorAdapter使用,核心关联方法是:runQueryOnBackgroundThread
对于AutoCompleteTextView如果设置游标适配器，那么每次当用户输入字符时就会执行一次查询，如果没实现runQueryOnBackgroundThread方法，那么自动选择出所有符合条件的cursor结果，所以为了实现自己的特殊查询则需要实现runQueryOnBackgroundThread 方法*/
<AutoCompleteTextView android:id="@+id/et_number"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:completionThreshold="1"
        android:hint="@string/please_enter_number"
        android:textSize="18sp"
        android:inputType="phone"/>
		
public class NewMessageActivity extends Activity{
	private AutoCompleteTextView et_number;
	private ContactAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message);		
		et_number = (AutoCompleteTextView) findViewById(R.id.et_number);	
		mAdapter = new ContactAdapter(this, null);
		et_number.setAdapter(mAdapter);	
		et_number.setOnItemClickListener(new MyOnItemClickListener());
	}
	//定义自动提示条目的点击事件
	private final class MyOnItemClickListener implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Cursor cursor = (Cursor) mAdapter.getItem(position);
			String number = cursor.getString(ContactAdapter.NUMBER_COLUMN_INDEX);
			et_number.setText(number);
		}		
	}
	//定义适配器
	public class ContactAdapter extends CursorAdapter {
	
	private LayoutInflater mInflater;
	private Context context;
	private final static int DISPLAY_NAME_COLUMN_INDEX = 1;
	public final static int NUMBER_COLUMN_INDEX = 2;

	public ContactAdapter(Context context, Cursor c) {
		super(context, c);
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = mInflater.inflate(R.layout.contact_item, null);
			ContactViews views = new ContactViews();
			views.tv_name = (TextView) view.findViewById(R.id.tv_name);
			views.tv_number = (TextView) view.findViewById(R.id.tv_number);
			
			view.setTag(views);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ContactViews views = (ContactViews) view.getTag();
			String name = cursor.getString(DISPLAY_NAME_COLUMN_INDEX);
			String number = cursor.getString(NUMBER_COLUMN_INDEX);
			views.tv_name.setText(name);
			views.tv_number.setText(number);
		}
		
		@Override
		public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		//注意做非空判断
			if(TextUtils.isEmpty(constraint)){
				return null;
			}
			//通过输入的数据模糊查询数据库
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			//select * from sms where number like '%1%'
			String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " like '%" + constraint + "%'";
			Cursor c = context.getContentResolver().query(uri,
														new String[]{ContactsContract.Contacts._ID,
														ContactsContract.Contacts.DISPLAY_NAME,
														ContactsContract.CommonDataKinds.Phone.NUMBER},
														selection, null, null);
			return c;
		}

		private final class ContactViews{
			TextView tv_name;
			TextView tv_number;
		}
	}
}