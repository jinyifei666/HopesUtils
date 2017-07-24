//优化的核心思想就是把view与view里的子view最大限度的复用,对于view而言,adapter自动记录了,是convertView
//而子view则没有,所以就要用一个内部类去存起来,view有一个方法setTag可以存一个对象,把子view存于内部类中,把内部存于view中,
//同时view.getTag就可以把对象取出来,强转成本来的类型,就可以得到里面的子view
注:
1.ListView.getCount()与ListView.getChildCount()区别:
//getCount()返回的是其 Adapter.getCount() 返回的值。也就是“所包含的 Item 总个数”。
//而getChildCount()(ViewGroup.getChildCount) 返回的是显示层面上的“所包含的子 View 个数”。也就是说getChildCount()返回的是当前可见的 Item 个数。
public class MainActivity extends Activity {

    private ListView calllog;
	private List<MyCallLog> list;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calllog = (ListView)findViewById(R.id.calllog);
        CallLogDao dao=new CallLogDao(this);
        list =dao.getCallog();
        calllog.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
	//先定义两个成员变量;
				ViewHolder holder;
				View view;
				//如果不存在,则创建
				if (convertView==null) {
					view = View.inflate(MainActivity.this, R.layout.listview_item, null);
					holder=new ViewHolder();
					holder.tv_name = (TextView) view.findViewById(R.id.tv_name//这里要注意要用产生的view去获得布局里的子view
					holder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
					holder.tv_duration = (TextView)view.findViewById(R.id.tv_duration);
					holder.type = (TextView) view.findViewById(R.id.type);
					view.setTag(holder);
				}else{//存在则直接拿来用
					view=convertView;
					holder=(ViewHolder) view.getTag();
				}
				//拿到holder就可以取到里面的所的对象了,并把进行相应的操作.
				MyCallLog myCallLog=list.get(position);
				holder.tv_name.setText(myCallLog.getName());
				holder.tv_phone.setText(myCallLog.getPhone());
				holder.tv_duration.setText(myCallLog.getDuration());
				holder.type.setText(myCallLog.getType());
				
				return view;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return list.get(position);
			}
			
			@Override
			public int getCount() {
				return list.size();
			}
		});
    }
	//定义子view的存储类
	static class ViewHolder {
		public TextView tv_phone;
		public TextView tv_time;
		public TextView tv_duration;
		public TextView type;
		public TextView tv_name;
	}
}
