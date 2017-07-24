//1,广播接收者接收电话号码
//注册权限:
<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
//注册receiver:
<receiver android:name=".receiver.OutCallReceiver" >
            <intent-filter android:priority="1000" >
                <action android:name="android.intent.action.NEW_OUTGOING_CALL" />
            </intent-filter>
        </receiver>
//接收广播代码
public class OutCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String data = getResultData();
		//定义一个拨号响应号码,一旦匹配,响应程序
		if ("8816".equals(data)) {
			Intent lostIntent = new Intent(context,LostFindActivity.class);
			//在非Activity中的context的上下文启动Activity时,需要手动设制一个标识,让其添加到任务栈,不然无法启动Activity.
			lostIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(lostIntent);
			setResultData(null);
			//abortBroadcast();
		}

	}
}

//2.广播接收者接收开机广播
//注册receiver:开机广播没有数据,所以不需要定义数据接收
<receiver android:name="com.example.receiver.SafeNumBroadcast" >
	<intent-filter android:priority="1000" >
		<action android:name="android.intent.action.BOOT_COMPLETED" />
	</intent-filter>
</receiver>

//3.广播接收者接收短信广播

//注册receiver:
	<receiver android:name=".receiver.SmsReceiver" >
		<intent-filter android:priority="1000" >
			<action android:name="android.provider.Telephony.SMS_RECEIVED" />
		</intent-filter>
	</receiver>
		public class SmsReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");//获得短信,是一个字节数组的数组.
		//遍历获得数据,里面是短信的字节数组
		for (Object pdu : pdus) {
		//通过字节数组获得短信对象
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);		
			String sender = smsMessage.getOriginatingAddress();//获得发送的电话号码	
			String body = smsMessage.getMessageBody();//获得发送的内容
			}
	}
}