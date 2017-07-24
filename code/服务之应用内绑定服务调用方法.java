//服务之应用内绑定服务调用方法
1.首先定义服务返回绑定的Binder
public class WatchDogService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	private class MyBinder extends Binder implements IService {
		@Override
		public void callMethodInService(String packname) {
			tempStopProtecet(packname);
		}
	}
	//需要给Activity绑定的方法
	private void tempStopProtecet(String packname) {
		tempStopProtectPacknames.add(packname);
	}
}
2.定义接口	
public interface IService {
	public void callMethodInService(String packname);
}
3.在Activity中绑定服务调用方法
public class EnterPasswrodActivity extends Activity {
	private MyConn conn;
	private IService iService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_password);
		//定义要绑定服务的意图
		Intent service = new Intent(this,WatchDogService.class);
		conn = new MyConn();
		//绑定服务
		bindService(service, conn, BIND_AUTO_CREATE);

	}
//定义连接
private class MyConn implements ServiceConnection{
//获得binder,强转回接口类型.
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		iService = (IService) service;
	}
	@Override
	public void onServiceDisconnected(ComponentName name) {			
	}		
}
//在需要的地方,调用了服务的方法
public void enter(View view){		
	iService.callMethodInService(packname);

}