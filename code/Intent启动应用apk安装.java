//传递一个Apk的file就可以
protected void installApk(File file) {
//参照需要启动的Activity的filter配置去进行Intent设制就可以
//		<action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <data android:scheme="content" />
//        <data android:scheme="file" />
//        <data android:mimeType="application/vnd.android.package-archive" />
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
//		intent.setType("application/vnd.android.package-archive");
//		intent.setData(Uri.fromFile(file));
		//注意:这两个要一起设两个参数,一个是uri,一个是type,因为单独设的话会出现清空前一个的设置.
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}
