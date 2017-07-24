//下拉伸缩ListView 
//定义ExpandableListView.xmllayout----定好map集合-----定义适配器继承BaseExpandableListAdapter---添加适配器---定义组里面的view事件(如果需要isChildSelectable返回true)
public class CommonNumberActivity extends Activity  {
	private ExpandableListView elv;
	private List<String> groupNames;
	private Map<Integer, List<String>> childrenCacheInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//定义数组,前面表示组索引,后面表示组内容view的集合
		childrenCacheInfos = new HashMap<Integer, List<String>>();
		setContentView(R.layout.activity_common_num);
		//可扩展的Listview,下拉Listview
		elv = (ExpandableListView) findViewById(R.id.elv);
		elv.setAdapter(new MyAdapter());
		listBrand_ELV.setGroupIndicator(null);//定义不显示默认箭头.
		listBrand_ELV.setDivider(null);//定义不显示默认的线条.
		// 展开级菜单,下面表示全部展开.
		for (int i = 0; i < paramObject.size(); i++) {
				listBrand_ELV.expandGroup(i);
			}
		//定义组view下的子view点击事件 ,这里是直接得到号码拨打电话
		elv.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				//截取电话号码:split用于分割字符串成对象.
				String number = childrenCacheInfos.get(groupPosition).get(childPosition).split("\n")[1];
				intent.setData(Uri.parse("tel:"+number));
				startActivity(intent);
				return false;
			}
		});
1.注意要在下面getView中设置view的监听:如://view.setOnClickListener(CommonNumberActivity.this);注:这里能用this则Activity必须实现OnClickListener接口.
2.isChildSelectable要返回true.
3.长按事件的处理.
			sellELV.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemLongClick(position);
				return false;
			}
		});
	}
	private void itemLongClick(int position){
		switch (position) {
		case -1://操作父item
			
			break;

		default://不是1,那就都是操作父view
			
			break;
		}
	}
//getGroupCount与getChildrenCount是先于getView执行的,所以可以把查询数据库获取数据的方法定义在这里,让其一组一组获取.
	private class MyAdapter extends BaseExpandableListAdapter {

		// 返回多少个分组 注:这个一定要
		@Override
		public int getGroupCount() {
			// return CommonNumDao.getGroupCount();
			groupNames = CommonNumDao.getGroupInfos();
			return groupNames.size();
		}
		//这个方法返回对应的每个分组的view个数 注:这个一定要
		@Override
		public int getChildrenCount(int groupPosition) {// 0 开始
			List<String> childreninfos;
		//childrenCacheInfos是一个Map<Integer, List<String>> 集合,那么Integer对应groupPosition索引
			//那么就可以通过groupPosition获得对应子组集合,从而集合个数就是这组的条目数
			if (childrenCacheInfos.containsKey(groupPosition)) {//这里首先判断一下是否集合里是否有这个组的数据
				childreninfos = childrenCacheInfos.get(groupPosition);
			} else {
				//如果没有就查询所有id ,获得集合并添加入,map中
				childreninfos = CommonNumDao.getChildrenInfosByPosition(groupPosition);
				childrenCacheInfos.put(groupPosition, childreninfos);
			}
			return childreninfos.size();
		}
		//返回对应的组对象的数据
		@Override
		public Object getGroup(int groupPosition) {
			return childrenCacheInfos.get(groupPosition);
		}
		//返回对应组中对应的子view对象的数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childrenCacheInfos.get(groupPosition).get(childPosition);
		}
		//返回对应的组索引
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		//返回对应的子view索引
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		//若hasStableIds()是true，那么getChildId必须要返回稳定的ID
		@Override
		public boolean hasStableIds() {
			//组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
			//返回值:返回一个Boolean类型的值，如果为TRUE，意味着相同的ID永远引用相同的对象。
			return false;
		}
		//返回对应的组view 注:必须有
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView tv;
			if(convertView!=null&&convertView instanceof TextView){
				tv = (TextView) convertView;
			}else{
				tv = new TextView(getApplicationContext());
			}
			//定义view的内容
			tv.setTextSize(25);
			tv.setTextColor(Color.RED);
			tv.setText("      " + groupNames.get(groupPosition));
			return tv;
		}
		//返回组view下的子view 注:必须有
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView tv;
			if(convertView!=null&&convertView instanceof TextView){
				tv = (TextView) convertView;
			}else{
				tv = new TextView(getApplicationContext());
			}
			tv.setTextSize(18);
			tv.setTextColor(Color.BLUE);
			
			tv.setText(childrenCacheInfos.get(groupPosition).get(childPosition));
			return tv;
		}
		//这信方法表示子view是否可以响应点击事件.true表示可以,false表示不可以.
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}