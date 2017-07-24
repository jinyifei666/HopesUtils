6.获得所有开机启动的应用名
public List<String> getStartupPackname() {
		//查询手机里面的应用程序的意图过滤器, 看哪个应用程序里面有 android.intent.action.BOOT_COMPLETED,有这个就表示会开机启动.
		List<String> packnames=new ArrayList<String>();
		PackageManager pm = getPackageManager();
		Intent intent = new Intent("android.intent.action.BOOT_COMPLETED");
		//获得
		List<ResolveInfo> infos = pm.queryBroadcastReceivers(intent, PackageManager.GET_INTENT_FILTERS);
		for(ResolveInfo info : infos){
			String receivername = info.activityInfo.name;//获得广播接收者的名字
			String packname = info.activityInfo.packageName;//包名
			packnames.add(packname);
		}
		return packnames;
}