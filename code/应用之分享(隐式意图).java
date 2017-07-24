/**
	 * 分享应用.
	 */
	private void shareApk() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		//下面这个信息随便定义
		intent.putExtra(Intent.EXTRA_TEXT,"推荐你使用一款软件.名称为:" + selectedAppInfo.getAppname() + ",版本:"+ selectedAppInfo.getVersion());
		startActivity(intent);
	}