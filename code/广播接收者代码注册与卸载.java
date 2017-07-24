//广播接收者可以通过代码创建与注册,这样就可以在代码中实现与其它功能(如服务)实现绑定,共生共灭,而不用去用标记.
//示例代码如下:
public class AddressService extends Service {

	private InnerOutcallReceiver outcallReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	//1.定义一个广播接收者内部类
	private class InnerOutcallReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		//定义需要的处理代码.....如:
			showAddress(getResultData());
			
		}
	}
	@Override
	public void onCreate() {
		super.onCreate();
		//2.建立接收者对象
		outcallReceiver = new InnerOutcallReceiver();
		//3.建立过滤器对象
		IntentFilter filter = new IntentFilter();
		//4.匹配动作,这里匹配的是对外拨号
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		//5.注册广播接收者
		registerReceiver(outcallReceiver, filter);
	}
	@Override
	public void onDestroy() {
		//卸载广播注册
		unregisterReceiver(outcallReceiver);
		outcallReceiver=null;
		super.onDestroy();
	}
}