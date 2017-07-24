ListView之分页加载
public class CallSmsSafeActivity extends Activity implements OnClickListener {
	public static final String TAG = "CallSmsSafeActivity";
	private ListView lv_call_sms;
	private View loading;
	private BlackNumberDao dao;
	private List<BlackNumberInfo> infos;
	private CallSmsAdapter adapter;
	private int maxnumber = 20;
	private int offset = 0;

	private int totalNumber;// 总共有多少条记录
	private int currentPage = 1; // 当前页

	private EditText et_page_number;
	private TextView tv_page_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		et_page_number = (EditText) findViewById(R.id.et_page_number);
		loading = findViewById(R.id.loading);
		//初始化第一页的数据
		dao = new BlackNumberDao(this);
		totalNumber = dao.getMaxNumber();
		tv_page_status = (TextView) findViewById(R.id.tv_page_status);
		tv_page_status.setText("当前/总:" + currentPage + "/"
				+ getTotalPagenumber(totalNumber) + "页");
		lv_call_sms = (ListView) findViewById(R.id.lv_call_sms);
		//条目长按事件
		lv_call_sms.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CallSmsSafeActivity.this,EditBlacknumberDialog.class);
				//传递修改的电话号码.
				MoblieSafeApplication app = (MoblieSafeApplication) getApplication();
				app.info = infos.get(position);
				startActivityForResult(intent, 0);
				return false;
			}
		});
		
		filldata();
		
		
		Intent intent = getIntent();
		String blacknumber = intent.getStringExtra("blacknumber");
		if(!TextUtils.isEmpty(blacknumber)){
			showAddBlackNumberDialog(blacknumber);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(TAG,"onnewintent");
		String blacknumber = intent.getStringExtra("blacknumber");
		if(!TextUtils.isEmpty(blacknumber)){
			showAddBlackNumberDialog(blacknumber);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==200){
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//获取总页数
	public int getTotalPagenumber(int totalNumber) {
		if (totalNumber % maxnumber == 0) {
			return totalNumber / maxnumber;//注意这里是除,上面判断是%
		} else {
			return totalNumber / maxnumber + 1;
		}
	}

	/**
	 * 页面跳转对应的事件
	 * 
	 * @param view
	 */
	public void jump(View view) {
		String pagenumberStr = et_page_number.getText().toString().trim();
		int pagenumber = Integer.parseInt(pagenumberStr);
		if (pagenumber == currentPage) {
			Toast.makeText(this, "就是当前页..", 0).show();
			return;
		}
		if (pagenumber > getTotalPagenumber(totalNumber)) {
			Toast.makeText(this, "超出页码范围..", 0).show();
			return;
		}

		// 1 -- 0(页码-1)*maxnumber ~19
		// 2 --- 20~39
		offset = (pagenumber - 1) * maxnumber;//计算应该下一页应该从第几行开始查
		currentPage = pagenumber;//每次下一页后表当前页码记录到currentPage
		//下一页后,更新页码显示
		tv_page_status.setText("当前/总:" + currentPage + "/"
				+ getTotalPagenumber(totalNumber) + "页");
		filldata();
	}

	/**
	 * 填充数据
	 */
	private void filldata() {
		MyAsyncTask task = new MyAsyncTask() {
			@Override
			public void onPreExecute() {
				loading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPostExecute() {
				loading.setVisibility(View.INVISIBLE);
				if (adapter == null) {
					adapter = new CallSmsAdapter();
					lv_call_sms.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void doInBackground() {
			//通过offset定义下一页数据的获取
				infos = dao.findByPage(maxnumber, offset);

			}
		};
		task.execute();
	}
//adtapter的实现类
	private class CallSmsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final BlackNumberInfo info = infos.get(position);
			View view;
			ViewHolder hodler;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView; // 复用历史缓存的view对象
				// Log.i(TAG,"使用缓存view"+position);
				hodler = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_callsms_item, null);
				// Log.i(TAG,"创建新的view"+position);
				// 寻找到孩子引用 把引用存起来.
				hodler = new ViewHolder();
				hodler.tv_mode = (TextView) view
						.findViewById(R.id.tv_call_sms_mode);
				hodler.tv_number = (TextView) view
						.findViewById(R.id.tv_call_sms_number);
				hodler.iv_callsms_delete = (ImageView) view
						.findViewById(R.id.iv_callsms_delete);
				view.setTag(hodler);
			}

			hodler.tv_number.setText(info.getNumber());
			if ("1".equals(info.getMode())) {
				hodler.tv_mode.setText("全部拦截");
			} else if ("2".equals(info.getMode())) {
				hodler.tv_mode.setText("电话拦截");
			} else if ("3".equals(info.getMode())) {
				hodler.tv_mode.setText("短信拦截");
			}
			//定义条目单击事件
			hodler.iv_callsms_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String number = info.getNumber();
					boolean result = dao.delete(number);
					if (result) {
						infos.remove(info);
						adapter.notifyDataSetChanged();
						totalNumber--;
						tv_page_status.setText("当前/总:" + currentPage + "/"
								+ getTotalPagenumber(totalNumber) + "页");
					} else {
						Toast.makeText(getApplicationContext(), "删除失败", 0)
								.show();
					}
				}
			});
			return view;
		}

	}

	/**
	 * view对象孩子引用的容器
	 * 
	 * @author Administrator
	 * 
	 */
	static class ViewHolder {
		TextView tv_number;
		TextView tv_mode;
		ImageView iv_callsms_delete;
	}
}