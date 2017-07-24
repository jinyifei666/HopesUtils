/*
*手势识别器的用法,用于Activity中定义监听手势滑动(左右滑动上下滑动都可以)事件.注:定义好后一定要在添加入onTouch方法中
*/
	//1.定义一个手势识别器
	private GestureDetector mGestureDetector;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config",MODE_PRIVATE);
		//2. 初始化手势识别器
		mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
			//当手指在屏幕上滑动的时候调用的方法			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//加速度有正负之分.右滑为正,左滑动为负,如果加速度过小,不促发事件
				if(Math.abs(velocityX)<200){
					Log.i(TAG,"移动的太慢,动作不合法");
					return true;
				}
				//这里只需要横滑如果是竖滑也不合法
				if(Math.abs(e1.getRawY()- e2.getRawY())>100){
					Log.i(TAG,"竖直方向移动距离过大,动作不合法");
					return true;
				}
				//这里getRawX表示相对于屏幕左边缘是相对于左边缘的距离,这里表示左滑进入下一页
				if((e1.getRawX()- e2.getRawX())>200){
					showNext();
					return true;
				}
				//这里表示右滑,进入前一页
				if((e2.getRawX()- e1.getRawX())>200){
					showPre();
					return true;
				}				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		
	}
	
	//3.让手势识别器生效
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);//这句别忘了.
		return true;
	}