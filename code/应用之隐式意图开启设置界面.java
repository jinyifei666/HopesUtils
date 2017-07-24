1.开应用设置界面从而在上面实现缓存清理等操作.
view.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		System.out.println("被点击了...");
		String packname = info.packname;
		if (Build.VERSION.SDK_INT >= 9) {//2.2以上版本这样开启
			// 开启设置界面.
			Intent intent = new Intent();
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			intent.setData(Uri.parse("package:"+ packname));
			startActivity(intent);
		} else {// 2.2 以下版本,则用这个方式开启设置界面
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addCategory("android.intent.category.VOICE_LAUNCH");
			intent.putExtra("pkg", packname);
			startActivity(intent);
		}
	}
});