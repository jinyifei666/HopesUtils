1.GestureDetector手势别器内部也是通过ScrollBy与ScrollView实现的.
@Override
public boolean onTouchEvent(MotionEvent event) {
//下面这样操作就可替换gestureDetector的onScroll方法了.
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:
		mLastX = event.getX();
		break;
	case MotionEvent.ACTION_MOVE:
		
		scrollBy((int) (mLastX - event.getX()), 0);
		
		mLastX = event.getX();
		
		break;
	case MotionEvent.ACTION_UP:
		moveToDest();
		break;

	}
	
	return true;
}
2.下面与下面是效果是等同的.都实现了左右滑动

gestureDetector = new GestureDetector(ctx, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				scrollBy((int)distanceX, 0);//关健代码
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			
			scrollBy((int) (mLastX - event.getX()), 0);
			
			mLastX = event.getX();
			
			break;
		case MotionEvent.ACTION_UP:
			moveToDest();
			break;
		}		
		return true;
	}