/**
	 * 通过包名开启一个应用程序
	 */
	private void startApk(String packname) {
		// 开启这个应用程序里面的第1个activity就把应用打开了.
		try {
			PackageInfo packinfo = getPackageManager().getPackageInfo(packname,PackageManager.GET_ACTIVITIES);//获得应用包对象
			ActivityInfo[] activityinfos = packinfo.activities;//获得所有的Activity对象
			if(activityinfos!=null&&activityinfos.length>0){
				ActivityInfo activityinfo = activityinfos[0];//获得第一个Activity对象
				String className = activityinfo.name;//获得Activity类名
				Intent intent = new Intent();
				intent.setClassName(selectedAppInfo.getPackname(), className);//通过应用的包名加Activity的类名定义的意图开启应用.
				startActivity(intent);
			}else{
				Toast.makeText(this, "无法启动应用程序!", 0).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "无法启动应用程序", 0).show();
		}

	}