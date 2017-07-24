//获得程序缓存 代码大小  数据大小.
//.注:android.content.pmaidl包名 还要两个aidl文件 IPackageStatsObserver.aidl 和PackageStats.aidl 把这两个拷贝到应用目录下.
private void getPackageSize(String packname) {
	try {
		//通过反射去调用PackageManager的getPackageSizeInfo隐藏方法去获得缓存大小.数据大小,代码大小
		Method method = PackageManager.class.getMethod(
				"getPackageSizeInfo", new Class[] { String.class,IPackageStatsObserver.class });
		//这个方法需要两个参数一个是包名,一个是IPackageStatsObserver的子类对象
		method.invoke(pm,new Object[] { packname, new MyObserver(packname) });
	} catch (Exception e) {
		e.printStackTrace();
	}
}
		//通过aidl把IPackageStatsObserver类传递过来,继承Stub类
private class MyObserver extends IPackageStatsObserver.Stub {
	private String packname;
	public MyObserver(String packname) {
		this.packname = packname;
	}
	//上面的invoke方法getPackageSizeInfo一旦执行成功,就会调用这个方法.
	@Override
	public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
			throws RemoteException {
		long cachesize = pStats.cacheSize;
		long codesize = pStats.codeSize;
		long datasize = pStats.dataSize;
	}
}