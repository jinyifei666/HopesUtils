Activity应用汇总
//只要有意图启动了这个Activity,不管这个Activity处于什么状态,都会执行以下方法,可以把要实现更新的数据放在这里.
@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(TAG,"onnewintent");
		String blacknumber = intent.getStringExtra("blacknumber");
		if(!TextUtils.isEmpty(blacknumber)){
			showAddBlackNumberDialog(blacknumber);
		}
	}