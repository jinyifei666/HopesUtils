//触摸监听
//注:如果同时定义了OnClickListener事件,那么必须返回false,如果没有定义,则必须返回true,表示消费这个触摸事件,才有效果.
//给图片添加一个触摸事件
		iv_drag_view.setOnTouchListener(new OnTouchListener() {
			
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://按下
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
					//在手指移动时循环去得到新坐标,重新设置位置,设置完后再从重获得开始位置,再去得新坐标,一直循环下去
				case MotionEvent.ACTION_MOVE://移动
					//移动后记录新位置
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					//获取新的坐标差
					int dx = newX - startX;
					int dy = newY - startY;
					//得到原来控件四个边离参照点的距离(参照点是屏幕左上角点)
					int l = iv_drag_view.getLeft();
					int t = iv_drag_view.getTop();
					int r = iv_drag_view.getRight();
					int b = iv_drag_view.getBottom();
					//得到四个边的新距离
					int newl = l + dx;
					int newr = r + dx;
					int newt = t + dy;
					int newb = b + dy;
					//设置新的位置
					//如果超过边距,则不设置,跳出重新设置
					if (newl<0||newr>screenWidth||newt<0||newb>screenHeight-30) {
						break;
					}
					iv_drag_view.layout(newl, newt, newr, newb);
					//tv_drag_view.getBottom()-tv_drag_view.getTop();
					//如果图片大于屏幕的一半
					if (newb>=screenHeight/2) {
						tv_drag_view.layout(tv_drag_view.getLeft(), 0, tv_drag_view.getRight(), tv_drag_view.getHeight());
					}else{
					//这里要注意屏幕高是包括最顶端的标题栏的,所以要减30
						tv_drag_view.layout(tv_drag_view.getLeft(), screenHeight-tv_drag_view.getHeight()-30, tv_drag_view.getRight(), screenHeight-30);
					}
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					//手指离开时,把坐标存起来,用于来电显示的坐标的位置
					Editor editor = sp.edit();
					editor.putInt("lastx", iv_drag_view.getLeft());
					editor.putInt("lasty", iv_drag_view.getTop());
					editor.commit();
					break;
				}
				return false;
			}
		});