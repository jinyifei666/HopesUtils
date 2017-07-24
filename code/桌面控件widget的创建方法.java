桌面widget浮动窗口
//1.定义一个类继承AppWidgetProvider
	
/**
 * 由于widget最短也要半小时更新一次,所以一般都是自定义其更新周期,用服务去实现了.定期在后台更新数据
 */
public class MyWidget extends AppWidgetProvider {
	// 不管创建还是删除都会执行
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	// 创建新的widget就会执行,
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Intent intent = new Intent(context, UpdateWidgetService.class);
		context.startService(intent);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	// 有widget移除就会执行
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Intent intent = new Intent(context, UpdateWidgetService.class);
		context.startService(intent);
		super.onDeleted(context, appWidgetIds);
	}

	// 第一个widget创建执行 ,用于初始化,比如开启一个服务
	@Override
	public void onEnabled(Context context) {
		Intent intent = new Intent(context, UpdateWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}

	// 最后一个widget删除执行,用于擦屁股,比如停止这个widget所执行的服务等
	@Override
	public void onDisabled(Context context) {
		Intent intent = new Intent(context, UpdateWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}
}
//2.在menifest.xml注册
 <receiver android:name=".MyWidget" >
		<intent-filter>
			<action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
		</intent-filter>
		<meta-data
			android:name="android.appwidget.provider"
			android:resource="@xml/example_appwidget_info" />
</receiver>
//3.在layout文件夹下定义桌面控件的样式
.....

//4.在res下创建一个xml文件夹,并在里面创建一个example_appwidget_info.xml文件
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:initialLayout="@layout/process_widget"//引入控件的样式
    android:minHeight="72dp"//最小高度
    android:minWidth="294dp"//最小宽度
    android:updatePeriodMillis="1800000" >//执行周期,最小半小时

</appwidget-provider>

//定义自定义的后台更新服务
public class UpdateWidgetService extends Service {
	private Timer timer;
	private TimerTask task;
	private AppWidgetManager awm ;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
	//获得AppWidgetManager管理器对象,这个对象用于更新widget
	/**
		 * AppWidgetManager可以用于更新widget方法为:updateAppWidget
		 * 还可以通过appWidgetId(对应布局文件的id)获得指定的Widget对象  public AppWidgetProviderInfo getAppWidgetInfo(int appWidgetId)
		 * 还可以获得所有已安装的Widget对象  public List<AppWidgetProviderInfo> getInstalledProviders()
		 * 详细可以查看源码
		 */
		awm = AppWidgetManager.getInstance(getApplicationContext());	
		// 开启定期执行的任务.
		timer = new Timer();
		task = new TimerTask() {
			
			@Override
			public void run() {
				//用于指定更新哪一个widget
				ComponentName provider = new ComponentName(getApplicationContext(), MyWidget.class);
				//获得widget控件对应布局view对象
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
				//通个view对象可以设置其布局下子view的内容.如setImage
				views.setTextViewText(R.id.process_count, "正在运行的软件:"+TaskUtils.getRunningProcessCount(getApplicationContext())+"个");
				views.setTextViewText(R.id.process_memory, "可用内存:"+Formatter.formatFileSize(getApplicationContext(), TaskUtils.getAvailRam(getApplicationContext())));
				Intent intent = new Intent();//自定义的广播事件. 
				intent.setAction("cn.itheima.killbgprocess");
				//自定义的广播接收者
//				<receiver android:name=".receiver.MyKillProcessReceiver" >
//	            <intent-filter>
//	                <action android:name="cn.itheima.killbgprocess" />
//	            </intent-filter>
//	            </receiver>
				//定义一个待定意图
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				//定义待定的点击事件
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				//更新widget
				awm.updateAppWidget(provider, views);
				
			}
		};
		timer.schedule(task, 1000, 2000);
		
		super.onCreate();
	}

	public void onDestroy() {
		timer.cancel();
		task = null;
		timer = null;
	};

}
