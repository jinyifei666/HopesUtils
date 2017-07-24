<uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/> 添加快捷方式权限：
<uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" /> 验证快捷方式是否存在权限：
<uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />删除快捷方式权限： 
public class ShortCutSample {
	/**
	 * 添加快捷方式
	 * */
	public static void creatShortCut(Activity activity, String shortcutName,
			int resourceId) {
		Intent intent = new Intent();
		intent.setClass(activity, activity.getClass());
		/* 以下两句是为了在卸载应用的时候同时删除桌面快捷方式 */
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		Intent shortcutintent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutintent.putExtra("duplicate", false);
		// 需要现实的名称
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		// 快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				activity.getApplicationContext(), resourceId);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 点击快捷图片，运行的程序主入口
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 发送广播。OK
		activity.sendBroadcast(shortcutintent);
	}

	/**
	 * 删除快捷方式
	 * */
	public static void deleteShortCut(Activity activity, String shortcutName) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		/** 改成以下方式能够成功删除，估计是删除和创建需要对应才能找到快捷方式并成功删除 **/
		Intent intent = new Intent();
		intent.setClass(activity, activity.getClass());
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		activity.sendBroadcast(shortcut);
	}

	/**
	 * 判断是否存在快捷方式
	 * */
	public static boolean hasShortcut(Activity activity, String shortcutName) {
		String url = "";
		int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
		/* 大于8的时候在com.android.launcher2.settings 里查询（未测试） */
		if (systemversion < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = activity.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
				new String[] { shortcutName }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}
}
