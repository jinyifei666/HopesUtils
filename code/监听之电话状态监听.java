//获取电话管理器
TelephonyManager teleService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//创建监听器
PhoneStateListener listen = new MyPhoneStateListener();
//添加监听器
teleService.listen(listen, PhoneStateListener.LISTEN_CALL_STATE);
//定义监听器
class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:// 电话空闲
				// 关闭显示
				if (view != null) {
					windowService.removeView(view);
					view = null;
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:// 响铃状态.
				// 开启显示
				showAddress(incomingNumber);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 正在通话
				// 间隔一会关闭显示
				new Thread(){public void run() {
					SystemClock.sleep(1000);
					if (view != null) {
						windowService.removeView(view);
						view = null;
					}
				};}.start();
				break;
			}
		}
		
	}