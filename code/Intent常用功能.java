/**
	 * 拨打电话
	 * @param number 电话号码字符串
	 */
	public void callPhone(String number) {
		Intent intent=new Intent();
		intent.setAction("android.intent.action.CALL");
		intent.setData(Uri.parse("tel:"+number));
		startActivity(intent);
	}　
　　意图类拨打电话
// 调用android的对象的api,拨打电话.
// 创建一个意图对象
Intent intent = new Intent();
setAction:给意图对象添加一个拨打电话的动作
intent.setAction(Intent.ACTION_CALL);
setData:给意图对象添加执行动作的数据
intent.setData(Uri.parse("tel:" + num));
/ 用意图对象当参数启动拨打电话的Activity,实际是sentMSN调用的内部一个方法去执行
startActivity(intent);
　　　　SmsManager类-发短信
单例,用静方法获取对象
SmsManager manager = SmsManager.getDefault();
用sendTextMessage发送短信
第一个参数:电话号码 第二个参数:信息中心号码,可以为null
第三个参数,短信内容.
第四个参数:接收回报:可以用null
第五个参数:发送回报,可以用null
manager.sendTextMessage(num, null, msn, null, null);
divideMessage方法:防止短信超长
ArrayList<String> smss = manager.divideMessage(content);
//遍历发送
			for (String sms : smss) {
				sm.sendTextMessage(number, null, sms, null, null);
			}
SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);	// 把字节数据封装为SmsMessage对象
			Date date = new Date(sms.getTimestampMillis());
			String address = sms.getOriginatingAddress();
			String body = sms.getMessageBody();