 /* 安装apk方法,用于安装下载好的Apk
	 * @param file
	 */
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		//注意:这两个要一起设两个参数,一个是uri,一个是type,因为单独设的话会出现.清空前一个的设置.
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}