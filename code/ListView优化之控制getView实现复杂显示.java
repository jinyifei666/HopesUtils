//控制ListView的getView方法实现复杂的数据显示.
public class TaskManagerActivity extends Activity {
	private TextView tv_process_count;
	private TextView tv_mem_info;

	private ListView lv_task_manager;
	private View loading;

	private List<TaskInfo> taskInfos;
	private List<TaskInfo> userTaskInfos;
	private List<TaskInfo> systemTaskInfos;

	private TaskAdapter adapter;

	private int runningProcessCount;
	private long availRam;
	private long totalRam;

	private boolean selectAll;
	private Button bt_select_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		tv_mem_info = (TextView) findViewById(R.id.tv_mem_info);
		tv_process_count = (TextView) findViewById(R.id.tv_process_count);
		bt_select_all = (Button) findViewById(R.id.bt_select_all);
		runningProcessCount = TaskUtils.getRunningProcessCount(this);
		availRam = TaskUtils.getAvailRam(this);
		totalRam = TaskUtils.getTotalRam();
		tv_process_count.setText("运行中进程:" + runningProcessCount + "个");
		tv_mem_info.setText("可用/总内存:"
				+ Formatter.formatFileSize(this, availRam) + "/"
				+ Formatter.formatFileSize(this, totalRam));
		loading = findViewById(R.id.loading);
		lv_task_manager = (ListView) findViewById(R.id.lv_task_manager);

		lv_task_manager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object obj = lv_task_manager.getItemAtPosition(position);
				if (obj != null) {
					TaskInfo taskInfo = (TaskInfo) obj;
					if (getPackageName().equals(taskInfo.getPackName())) {// 我自己.
						return;
					}
					if (taskInfo.isChecked()) {
						taskInfo.setChecked(false);
					} else {
						taskInfo.setChecked(true);
					}
					adapter.notifyDataSetChanged();// 刷新界面.
				}
			}
		});

		new MyAsyncTask() {

			@Override
			public void onPreExecute() {
				loading.setVisibility(View.VISIBLE);

			}

			@Override
			public void onPostExecute() {
				loading.setVisibility(View.INVISIBLE);
				adapter = new TaskAdapter();
				lv_task_manager.setAdapter(adapter);
			}

			@Override
			public void doInBackground() {
				taskInfos = TaskInfoProvider
						.getTaskInfos(getApplicationContext());
				userTaskInfos = new ArrayList<TaskInfo>();
				systemTaskInfos = new ArrayList<TaskInfo>();
				for (TaskInfo taskinfo : taskInfos) {
					if (taskinfo.isUserTask()) {
						userTaskInfos.add(taskinfo);
					} else {
						systemTaskInfos.add(taskinfo);
					}
				}

			}
		}.execute();
	}

	private class TaskAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean showsystem = sp.getBoolean("showsystem", false);
			if (showsystem) {
				return userTaskInfos.size() + 1 + systemTaskInfos.size() + 1;
			}else{
				return userTaskInfos.size() + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			if (position == 0) {
				return null;
			} else if (position == (userTaskInfos.size() + 1)) {
				return null;
			} else if (position <= userTaskInfos.size()) {
				int newpositon = position - 1;
				return userTaskInfos.get(newpositon);
			} else {
				int newpositon = position - 1 - userTaskInfos.size() - 1;
				return systemTaskInfos.get(newpositon);
			}

		}

		@Override
		public long getItemId(int position) {
			return position;
		}
//核心代码,通过判断去实现不同时刻显示不同数据
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TaskInfo taskinfo;
			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(R.color.gray);
				tv.setTextSize(18);
				tv.setText("用户进程:" + userTaskInfos.size() + "个");
				return tv;
			} else if (position == (userTaskInfos.size() + 1)) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(R.color.gray);
				tv.setTextSize(18);
				tv.setText("系统进程:" + systemTaskInfos.size() + "个");
				return tv;
			} else if (position <= userTaskInfos.size()) {
				int newpositon = position - 1;
				taskinfo = userTaskInfos.get(newpositon);
			} else {
				int newpositon = position - 1 - userTaskInfos.size() - 1;
				taskinfo = systemTaskInfos.get(newpositon);
			}

			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.list_task_item, null);
				holder = new ViewHolder();
				holder.tv_mem = (TextView) view.findViewById(R.id.tv_task_mem);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_task_name);
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_task_icon);
				holder.cb = (CheckBox) view.findViewById(R.id.cb_task_status);
				view.setTag(holder);
			}

			holder.iv_icon.setImageDrawable(taskinfo.getTaskIcon());
			holder.tv_name.setText(taskinfo.getTaskName());
			holder.tv_mem.setText("内存占用:"
					+ Formatter.formatFileSize(getApplicationContext(),
							taskinfo.getMemsize()));
			holder.cb.setChecked(taskinfo.isChecked());
			if (getPackageName().equals(taskinfo.getPackName())) {// 我自己.
				holder.cb.setVisibility(View.INVISIBLE);
			} else {
				holder.cb.setVisibility(View.VISIBLE);
			}

			return view;
		}

	}

	static class ViewHolder {
		TextView tv_name;
		TextView tv_mem;
		ImageView iv_icon;
		CheckBox cb;
	}

	/**
	 * 全选
	 * 
	 * @param view
	 */
	public void selectAll(View view) {
		if (selectAll) {// 已经全选了 ,设置为全不选
			for (TaskInfo info : userTaskInfos) {
				info.setChecked(false);
			}
			for (TaskInfo info : systemTaskInfos) {
				info.setChecked(false);
			}
			adapter.notifyDataSetChanged();
			selectAll = false;
			bt_select_all.setText("全选");
		} else { // 没有全部选择,设置全选
			for (TaskInfo info : userTaskInfos) {
				info.setChecked(true);
				if (getPackageName().equals(info.getPackName())) {// 我自己.
					info.setChecked(false);
				}
			}
			for (TaskInfo info : systemTaskInfos) {
				info.setChecked(true);
			}
			adapter.notifyDataSetChanged();
			selectAll = true;
			bt_select_all.setText("全不选");
		}
	}

	/**
	 * 一键清理进程
	 */
	public void killAll(View view) {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		int count = 0;
		long savedMem = 0;
		List<TaskInfo> killedTasks = new ArrayList<TaskInfo>();
		for (TaskInfo info : userTaskInfos) {
			if (info.isChecked()) {
				am.killBackgroundProcesses(info.getPackName());
				count++;
				savedMem += info.getMemsize();
				killedTasks.add(info);
			}
		}
		for (TaskInfo info : systemTaskInfos) {
			if (info.isChecked()) {
				am.killBackgroundProcesses(info.getPackName());
				count++;
				savedMem += info.getMemsize();
				killedTasks.add(info);
			}
		}
		String memStr = Formatter.formatFileSize(this, savedMem);
		// Toast.makeText(this, "杀死了"+count+"个进程,释放了"+memStr+"的内存", 1).show();

		MyToast.show(R.drawable.notification, "杀死了" + count + "个进程,释放了"
				+ memStr + "的内存", this);
		// 选择了杀死哪个条目 把这个条目从界面上移除.
		for (TaskInfo info : killedTasks) {
			if (info.isUserTask()) {
				userTaskInfos.remove(info);
			} else {
				systemTaskInfos.remove(info);
			}
		}
		adapter.notifyDataSetChanged();
		runningProcessCount -= count;
		availRam += savedMem;
		tv_process_count.setText("运行中进程:" + runningProcessCount + "个");
		tv_mem_info.setText("可用/总内存:"
				+ Formatter.formatFileSize(this, availRam) + "/"
				+ Formatter.formatFileSize(this, totalRam));
	}

	public void setting(View view) {
		Intent intent = new Intent(this,TaskSettingActivity.class);
		startActivityForResult(intent, 0);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==200){
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}