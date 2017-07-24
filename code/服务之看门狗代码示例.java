Service之看门狗经典代码
/**
 * 服务启动则创建一个不断执行的线程,不断监控当前正在运行的任务栈,并获得最上面的任务栈的最上面的Activity所属的应用名
 * 查看应用是否在锁定应用的数据库中
 * 如果在,则启动输入密码的Activity.
 * 一个应用解锁后,就不再锁定这个应用了,实现:自定义一个广播接收者,如果应用被解锁,让应用发送一个这个类型的广播,然后一旦接收到这个类型广播就把应用添加进临时被解锁集合
 * 避免频繁查询数据库,则可把数据查询出来到一个集合中.
 * 为了保证数据库与集合同步,则注册一个自定义内容观察者,让数据库增加或删除则重新获取锁定集合
 * 为了不一直在后台运行,节省电,就定义屏幕关屏和锁屏的广播接收者,让线程在关屏后停止,解锁屏幕后重新开启线程.
 * 为了避免密码输入框所属应用有Activity没销毁时,解锁后进入密码输入框所属应用没销毁Activity,则让输密码Activity启动模式为singleInstance
 * <activity
            android:name=".EnterPasswrodActivity"
            android:excludeFromRecents="true"//避免这个Activity出现在近期开启的任务中
            android:launchMode="singleInstance" >
    </activity>
 * 服务一旦停止,释放资源,卸载所有广播接收者,观察者,停止线程,至用到的对象为空...
 * @author fada
 *
 */
public class WatchDogService extends Service {
	private ActivityManager am;
	private AppLockDao dao;
	private Intent intent;
	private boolean flag;
	private List<String> tempStopProtectPacknames;
	private InnerReceiver receiver;
	private InnerLockScreenReceiver lockScreenReceiver;
	private InnerUnLockScreenReceiver unlockScreenReceiver;
	
	private List<String> lockedPackNames;

	private MyObserver observer;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
//接收到解锁广播,则把应用添加进临时解锁集合
	private class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String packname = intent.getStringExtra("stoppackname");
			tempStopProtectPacknames.add(packname);
		}
		
	}
	//哪果屏幕关闭锁定,设标记则停止看门口线程
	private class InnerLockScreenReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			tempStopProtectPacknames.clear();
			flag = false;
		}
		
	}
	//如果屏幕解锁,且线程不在执行,重新开始执行看门狗线程
	private class InnerUnLockScreenReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(!flag){//保险 
				startWatchDog();
			}
		}
		
	}
	//初始化需要的广播,对象,集合数据
	@Override
	public void onCreate() {
		//定义一个集合接收临时被解锁的应用.
		tempStopProtectPacknames = new ArrayList<String>();
		//定义一个广播接收者,如果应用被解锁,让应用发送一个这个类型的广播,然后一旦接收到这个类型广播就把应用添加进临时被解锁集合
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("cn.itheima.stopprotect");
		registerReceiver(receiver, filter);
		//定义锁屏的广播接收者
		lockScreenReceiver = new InnerLockScreenReceiver();
		IntentFilter lockFilter = new IntentFilter();
		lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
		lockFilter.setPriority(1000);
		registerReceiver(lockScreenReceiver, lockFilter);
		//定义解锁屏幕的广播接收者
		unlockScreenReceiver = new InnerUnLockScreenReceiver();
		IntentFilter unlockFilter = new IntentFilter();
		unlockFilter.addAction(Intent.ACTION_SCREEN_ON);
		unlockFilter.setPriority(1000);
		registerReceiver(unlockScreenReceiver, unlockFilter);
		
		//获得ActivityManager对象
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		dao = new AppLockDao(this);
		lockedPackNames = dao.findAll();//查找到所有被锁定应用的集合
		//定义出启动输入密码的Activity意图
		intent = new Intent(this, EnterPasswrodActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//在非Activity需要设这个标记才能启动Activity
		//注册一个观察者,监控锁定应用数据库的修改(增加或减少),则重新获取锁定集合
		observer = new MyObserver(new  Handler());
		getContentResolver().registerContentObserver(AppLockDao.uri, true, observer);
		
		
		super.onCreate();
		// 开启看门狗 监视 当前系统的程序运行信息.
		startWatchDog();

	}

	private void startWatchDog() {
		new Thread() {
			public void run() {
				flag = true;//这个保证,只要线程开启,就会把标记改成执行状态.
				while (flag) {//用标记控制这个循环是否执行
					//得到当前所有正在执行的任务栈,一个任务栈通常而言代表一个应用.
					List<RunningTaskInfo> infos = am.getRunningTasks(1);
					RunningTaskInfo taskinfo = infos.get(0);// 最新打开的任务栈.
					//ComponentName用于标识一个组件,可以返回标识组件对应的应用名.
					ComponentName topActivity = taskinfo.topActivity;// 得到栈顶的activity
					//或得这个Activity对应应用的包名													// 用户可见的activity
					String packname = topActivity.getPackageName();
					//if (dao.find(packname)) {// 需要保护这个应用 查询数据库 慢!
					if(lockedPackNames.contains(packname)){ //查询内存 
						// 判断当前包名是否要临时的停止保护.
						if (tempStopProtectPacknames.contains(packname)) {

						} else {
							intent.putExtra("packname", packname);
							startActivity(intent);
						}
					}
					try {
						Thread.sleep(30);//程序等一会,节省一下资源,并且让后台回收下垃圾,如果手机效率高,那么就时间设短一点,反之,就长一点.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	@Override
	public void onDestroy() {
		flag = false;//如果服务停止,则让循环停止,线程就会结束
		unregisterReceiver(receiver);//卸载广播接收者
		receiver = null;
		unregisterReceiver(lockScreenReceiver);
		lockScreenReceiver = null;
		getContentResolver().unregisterContentObserver(observer);  
		observer = null;
		unregisterReceiver(unlockScreenReceiver);
		unlockScreenReceiver = null;
		super.onDestroy();
	}
	//定义观察者,一但被保护应用数据库有修改,则重新获取被保护应用集合
	private class MyObserver extends ContentObserver{

		public MyObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			lockedPackNames = dao.findAll();
			super.onChange(selfChange);
		}
		
	}
}
