1.判断网格状态是否可用
public boolean getNetWorkStatus() {

		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null) {//如果连接对象不为空,表示网络可用.
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		//如果没有网络,弹出对话框询问用户是否要设置.
		if (!netSataus) {
			Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
					.setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					//进入设置界面时进行设置.
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					startActivityForResult(mIntent, 0);
				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		}
		return netSataus;
	}