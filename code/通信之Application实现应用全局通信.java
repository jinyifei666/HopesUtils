1.记得要在menifest.xml里面注册
<application
        android:name="MoblieSafeApplication"//这就是注册
        android:debuggable="false"
        android:icon="@drawable/shenmatran"//图标
        android:label="@string/app_name"//
//Application一个应用中只有一个,里面存的数据,整个应用都有效,全局有效.
1.可以通过下列方式获取对象.
//MoblieSafeApplication app = (MoblieSafeApplication) getApplication();
//info = app.info;
//app.info = null;
public class MoblieSafeApplication extends Application {
	public BlackNumberInfo info;//定义需要存的数据,如一个对象,保存起来.
	@Override
	public void onCreate() {
		//重新设置程序的异常处理器.		
		Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());		
		super.onCreate();
	}
}
