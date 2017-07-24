//获得手机sim卡序列号
public  String getSimNum() {
	TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	String simnum = tm.getSimSerialNumber();
	}