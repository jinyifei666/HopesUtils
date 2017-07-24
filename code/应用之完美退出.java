Android完美退出的方法
1.首先定义一个Application子类,注(Application本身已是单例!)
public class MyApp extends Application {
//定义list集合
	private List<Activity> list=new LinkedList<Activity>();
	private static MyApp app;//对象
	//定义添加Activity方法
	public void addActivity(Activity activity){
		list.add(activity);
	}
	//定义程序退出方法
	public void exit(){
		for(Activity activity:list){
			activity.finish();
		}
		System.exit(0);;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		app=this;//注意:这里因为已是单例,所以要这样把对象赋值过去
		// 注册crashHandler全局异常捕获
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}
}
2.定义一个baseActivity
public abstract class BaseActivity extends Activity implements OnClickListener{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
			MyApp.app.addActivity(this);
			}
			
		//这里利用菜单退出
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_main, menu);
			return true;
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			//这里可以定义一个确认对话框
			//showConfirmDialog("确定要退出吗", 111);
			MyApp.app.exit();//调用退出方法退出
			return super.onOptionsItemSelected(item);
		}
}
3.应用的每个Activity继承baseActivity