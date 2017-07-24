ListView的动态加载
private ListView lv_select_contact;
	private List<ContactInfo>  infos;
	private LinearLayout loading;
	private String limit="_id desc limit 30 offset ";//这里面的30表示每次查出的数据条数,自已定义
	private int offset;//表示从第行数据开始查
	private int total;//这个表示总条目数
	private ContactAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		lv_select_contact = (ListView) findViewById(R.id.lv_select_contact);
		loading = (LinearLayout) findViewById(R.id.loading);
		refreshData();//首先第一次先展示一部分
		//监听Listview滚动.通过offset的变化去实现数据的变化
		lv_select_contact.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState==OnScrollListener.SCROLL_STATE_IDLE) {//SCROLL_STATE_IDLE表示当滚动停止状态.
					int position = lv_select_contact.getLastVisiblePosition();//获得当前可见条目的最后一行索引
					int endPosition=infos.size();//获得当前list的数据最后一行索引
					if (position==endPosition-1) {
						offset+=30;
						if (offset>total) {//如果大于总条目表示数据加载完了
							Toast.makeText(SelectContactActivity.this, "没有更多数据了", 1).show();
						}
						refreshData();//刷新数据
					}
				}
				
			}
			//这个方法用于获得一些滚动的数据
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		//定义条目的点击事件
		lv_select_contact.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContactInfo info = infos.get(position);
				String phone = info.getPhone();
				Intent data = new Intent();
				data.putExtra("phone", phone);
				setResult(0, data);
				finish();
			}
		});
		
	}
//动态显示获取数据的方法
	private void refreshData() {
		new  MyAsynTask(){
			@Override
			public void beforeTask() {
				loading.setVisibility(View.VISIBLE);
			}
			@Override
			public void runTask() 
			//执行查询前,得到总条目
				if (total==0) {
					total=ContactInfoProvider.getTotal(SelectContactActivity.this);
				}
				//得到adapter需要的数据集合,没有直接创建
				if (infos==null) {
					infos = ContactInfoProvider.getContactInfos(SelectContactActivity.this,limit+0);
				}else{
				//有的话直接把得到的集合添加进去
					infos.addAll(ContactInfoProvider.getContactInfos(SelectContactActivity.this,limit+offset));
				}
			}
			@Override
			public void afterTask() {
			//执行后关闭进度条
				loading.setVisibility(View.INVISIBLE);
				if (adapter==null) {//如果adapter为空则应该是第一次添加,那么创建一个
					adapter=new ContactAdapter();
					lv_select_contact.setAdapter(adapter);
				}else{
				//如果有就直接刷新,这样就避免了每次加载完都跳到第一行.
				//但记住,这个跟数据库无直接关联,如果数据集合没有变化,这个是没有效果的,只有从重查询数据库得到新集合才可以..
					adapter.notifyDataSetChanged();
				}
			}}.startTask();
	}
	//添加适配器
	private class ContactAdapter extends BaseAdapter{

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
			ViewHolder hodler;
			View view ;
			if (convertView!=null&&convertView instanceof RelativeLayout) {
				view = convertView;
				hodler=(ViewHolder) view.getTag();
			}else{
				view = View.inflate(getApplicationContext(), R.layout.list_contact_item, null);
				hodler=new ViewHolder();
				hodler.tv_name=(TextView) view.findViewById(R.id.tv_contact_name);
				hodler.tv_phone=(TextView) view.findViewById(R.id.tv_contact_phone);
				view.setTag(hodler);
			}
			ContactInfo info = infos.get(position);
			hodler.tv_name.setText(info.getName());
			hodler.tv_phone.setText(info.getPhone());
			return view;
		}
		
	}
	//对象容器
	static class ViewHolder {
		TextView tv_name;
		TextView tv_phone;
	}
}