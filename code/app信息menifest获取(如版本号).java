//app版本号的获取
private String getVersion() {
	PackageManager pm=getPackageManager();
	try {
		PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
		return  packageInfo.versionName;
		
	} catch (NameNotFoundException e) {
		e.printStackTrace();
		return "";
	}
}

