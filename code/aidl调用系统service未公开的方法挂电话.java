package com.example.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephony.Stub;
import com.example.dao.db.BlackNumberDao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class BlackService extends Service {

	private TelephonyManager telService;
	private PhoneStateListener listener;
	private BlackNumberDao dao;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	@Override
	public void onCreate() {
		dao = new BlackNumberDao(this);
	telService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	listener=new MyPhoneStateListener();
	//前一个参数表示是监听器对象,后面表示是监听器类型
	telService.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}
	//定义一个电话状态监听器,这个监听器是一个类,里面做了空实现,所以要用要重写里面的onCallStateChanged方法
	public class MyPhoneStateListener extends PhoneStateListener{
		private long startTime;

		//这里只要电话响就拦截,所以只监听这一个状态就行
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				long endTime = System.currentTimeMillis();
				long time=endTime-startTime;
				if (time<3000) {
					killCall();
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				startTime = System.currentTimeMillis();
				String  blockmode = dao.findMode(incomingNumber);
				if ("1".equals(blockmode)||"2".equals(blockmode)) {
					killCall();
				}
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}

	}
	//挂断电话
	private void killCall() {
		//通过反射获得管理器对象,用管理器对象获得其getService方法的对象.getMethod前面的参数表示方法名,后面的表示方法参数类型的.class
		try {
			Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
			//获得方法对象后就可用方法对象的invoke方法去执行方法了,这个方法是返回一个对应的管理器,
			IBinder binder = (IBinder) method.invoke(null, new Object[]{TELEPHONY_SERVICE});
			//用aidl技术把这个管理器对象,转换为ITelephony对象
			ITelephony iTelephony = Stub.asInterface(binder);
			//用这个对象就可以动态调用这个service里的方法
			iTelephony.endCall();//表示挂电话--记住要添加拨打电话权限
		//	iTelephony.dial(number);//表示把号码加入到拨号界面,但先不拨打
		//	iTelephony.call(number);//这是直接拨打电话了
//以上这些方法如果不用反射和aidl而用telService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);获得对象,是调用不到的.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
