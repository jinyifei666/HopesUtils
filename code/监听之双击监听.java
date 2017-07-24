1.点击监听之双击.
//处理双击居中	
iv_drag_view.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View v) {
		//当第一个时间大于0,表示已经点击了一次了
		if (fristTime>0) {
			long secondTime = System.currentTimeMillis();
			if (secondTime-fristTime<500) {
				iv_drag_view.layout((screenWidth-iv_drag_view.getWidth())/2, iv_drag_view.getTop(), (screenWidth+iv_drag_view.getWidth())/2, iv_drag_view.getBottom());
				Editor editor = sp.edit();
				editor.putInt("lastx", iv_drag_view.getLeft());
				editor.putInt("lasty", iv_drag_view.getTop());
				editor.commit();
			}
				fristTime=0;//只要二次点击完了,就让第一次的时间为0
		}
		fristTime = System.currentTimeMillis();
		//如果点完第一次后超时了,就自动清除第一次点击时间
		new Thread(){
			public void run() {
				SystemClock.sleep(500);
				fristTime=0;
			};
		}.start();
	}
});
