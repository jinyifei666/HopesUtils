ProgressDialog pd;
public void getDialogAndProgress() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ConversationActivity.this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.delete);
		builder.setMessage(R.string.delete_dialog);
		builder.setCancelable(false);
		//确定按钮事件,添加进度条,开始删除.
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//新建一个进度条
				pd = new ProgressDialog(ConversationActivity.this);
				pd.setIcon(android.R.drawable.ic_dialog_alert);
				pd.setTitle(R.string.delete);
				pd.setMax(multSelected.size());
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//进度条风格
				//给进度条设置取消按钮   	
				pd.setButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//终止短信删除
						isDelete = false;
						pd.dismiss();//关闭进度条
					}
				});
				//添加disMiss监听处理  修改Activity模式
				pd.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						changeMode(DISPLAYMODE.list);
						Editor editor = sp.edit();
						editor.putBoolean("mode", false);
						editor.commit();
						
					}
				});
				pd.show();//显示进度条
				isDelete = true;//开启删除开关
				//具体要执行的方法
				new Thread(new DeleteTask()).start();//删除所选
				
			}
		});
		//对话框取消按钮事件,什么也不做
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
	//用build把对话框创建出来
		AlertDialog dialog = builder.create();
	//显示对话框
		dialog.show();
}
//删除信息的任务类
	private class DeleteTask implements Runnable{

		@Override
		public void run() {
			
			ArrayList<String> list = new ArrayList<String>(multSelected);
			
			for(int i = 0; i < list.size(); i++){
				//是否允许删除
				if(!isDelete){
					return;
				}
				Uri uri = Uri.withAppendedPath(Uri.parse("content://sms/conversations"), list.get(i));
				getContentResolver().delete(uri, null, null);
				//可以通过调用setProgress(int)设置当前进度百分比或者调用incrementProgressBy(int)方法增加进度值
				//	pd.setProgress(i);
				pd.incrementProgressBy(1);
				SystemClock.sleep(2000);
			}
			pd.dismiss();
		}
	}