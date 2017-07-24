//listview滑动监听
lv_app_manager.setOnScrollListener(new OnScrollListener() {

		//如果滚动状态发生改变时,调用这个方法.		
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//scrollState有三种状态:可以根据三种状态的不同去实现不同的操作.
		1.当屏幕停止滚动时为0:int SCROLL_STATE_IDLE = 0:
		2.当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1:int SCROLL_STATE_TOUCH_SCROLL = 1;
		3.由于用户的操作，屏幕产生惯性滑动时为2 :int SCROLL_STATE_FLING = 2;
	}
	
	// 当listview滚动的时候 调用onscroll的方法.
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
	
	1.firstVisibleItem:表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置（下标从0开始） 
	2.visibleItemCount:表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数 
	3.totalItemCount:表示ListView的ListItem总数
	4.listView.getLastVisiblePosition():表示在现时屏幕最后一个ListItem(最后ListItem要完全显示出来才算,	  在整个ListView的位置（下标从0开始） 
	}
});