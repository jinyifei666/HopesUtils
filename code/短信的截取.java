package com.itheima.blacklist;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
//手机收到短信就会发有序短信广播,可以在其它短信应用收到之前中断广播,且保存短信内容.
public class SmsRecever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");		// 获取短信数据(多段),短信是以pdus为名字的数组存在intent中的.
		for (Object pdu : pdus) {										// 循环遍历
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);	// 把字节数据封装为SmsMessage对象
			Date date = new Date(sms.getTimestampMillis());//获得短信
			String address = sms.getOriginatingAddress();//获得地址
			String body = sms.getMessageBody();//获得短信主体内容
			System.out.println(date + " " + address + " " + body);
			
			if ("18600012345".equals(address))//如果是某个号码,则中断.
				abortBroadcast();
		}
	}

}
