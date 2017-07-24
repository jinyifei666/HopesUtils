package pulldown.refresh;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyListView extends ListView implements OnScrollListener {
	private int state;
	private static final int PULL_TO_REFRESH = 0;// 下拉刷新
	private static final int RELEASE_TO_REFRESH = 1;// 松开刷新
	private static final int REFRESHING = 2;// 正在刷新
	private static final int DONE = 3;// 刷新完成

	private ImageView arrow;
	private ProgressBar refresh;
	private TextView pullTV;
	private TextView timeTV;
	private View headView;
	private int headContentHeight;
	private int firstVisibleIndex;
	private boolean isRecord;
	private float startY;
	private float temp;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isBack;
	private OnRefreshListener refreshListener;
//以下三个构造方法,全部都要加载headView,因为不同时期创建对象用的构造也不一样
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public MyListView(Context context) {
		super(context);
		init(context);
	}
//定义监听接口
	public interface OnRefreshListener {
		abstract void onRefresh();
	}
//提供设制方法.
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		refreshListener = onRefreshListener;
	}
//定义刷新方法.并定义在执行刷新的操作中,记住作非空判断(或者在判断监听被添加时,用布尔标记下),因这个方法是监听器的方法,也就是有监听才会执行.(也就是别的对象传了实现了监听对象进来)
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 初始化headView.切记:在哪个LayoutView下的子view,要用这个View要身去获得.
	private void init(Context context) {
		headView = View.inflate(context, R.layout.header, null);
		arrow = (ImageView) headView.findViewById(R.id.arrow);
		refresh = (ProgressBar) headView.findViewById(R.id.refresh);
		pullTV = (TextView) headView.findViewById(R.id.pullTV);
		timeTV = (TextView) headView.findViewById(R.id.timeTV);
		//定义控件最小显示宽高
		arrow.setMinimumWidth(70);
		arrow.setMinimumHeight(50);
		measureView(headView);
		//因为这个要用到的只是高度,所以只需要获得高度就可以
		headContentHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();// 重绘
		addHeaderView(headView);// 添加
		setOnScrollListener(this);
		//定义好动画.
		// 箭头由下到上
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(250);// 动画运行时间
		animation.setFillAfter(true);
		animation.setInterpolator(new LinearInterpolator());
		// 箭头由上到下
		reverseAnimation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		reverseAnimation.setDuration(200);// 动画运行时间
		reverseAnimation.setFillAfter(true);
		/**
		 * Interpolator 定义了动画的变化速度，可以实现匀速、正加速、负加速、无规则变加速等；
			AccelerateDecelerateInterpolator，延迟减速，在动作执行到中间的时候才执行该特效。
			AccelerateInterpolator, 会使慢慢以(float)的参数降低速度。
			LinearInterpolator，平稳不变的
			DecelerateInterpolator，在中间加速,两头慢
			CycleInterpolator，曲线运动特效，要传递float型的参数。
		 */
		reverseAnimation.setInterpolator(new LinearInterpolator());
	}
//这个方法实际是包装了measure方法,measure方法又包装了onMeasure方法.作用是父控件在显示子控件是回调,以得到子控件的的宽高.从而显示.
	private void measureView(View child) {
		ViewGroup.LayoutParams lp = child.getLayoutParams();
		if (lp == null) {
			lp = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		//这个方法最终还是调用 MeasureSpec.makeMeasureSpec方法.
		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
		} else {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childMeasureWidth, childMeasureHeight);

	}

	//
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1； 
		//由于用户的操作，屏幕产生惯性滑动时为2 
		//数据全部显示出来时运行此处代码，如果要实现分页功能，在这里加载下一页的数据 
	}
//实现OnScrollListener用于获得当前的值
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstVisibleIndex = firstVisibleItem;
//		 firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)在整个ListView的位置（下标从0开始） 
//		visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数 
//		totalItemCount表示ListView的ListItem总数 
//		listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始） 
	}
	//定义屏幕触摸事件.核心逻辑在这里
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 当屏幕被触摸且之前没有记录时,开始记录
			if (firstVisibleIndex == 0 && !isRecord) {
				startY = ev.getY();
				isRecord = true;
			}
			break;
			//滑动有四种状态
		case MotionEvent.ACTION_MOVE:
			if (isRecord) {//headView要出来的前提是当前View的第一行为ListView的首行
				temp = ev.getY();//首先记录上下移动的距离.
				//首先:在滑动时,页面不处于松手刷新提示及正在刷新提示,则根据其滑动距离是否超过view宽度去判断是否
				if (state != RELEASE_TO_REFRESH && state != REFRESHING) {
					if ((temp - startY) / 3 >= headContentHeight) {
						// 如果大于控件宽度,那么显示松开刷新
						state = RELEASE_TO_REFRESH;
						isBack = true;//一旦进入松开刷新,会改变动化,所以要开启条件让下拉刷新时切换动画.
						changeOfHeadViewState();

					} else {
						// 只要下拉不大于控件宽度,都显示下拉刷新
						state = PULL_TO_REFRESH;
						changeOfHeadViewState();
					}
				}
				//如果处于松手刷新提示状态,则一旦把headView推至与上边重合,则让显示下拉刷新提示.
				if (state == RELEASE_TO_REFRESH) {
					setSelection(0);//防止多页时,ListView首行发生了改变,导致这个方法条件无法满足无法执行
					if ((temp - startY) / 3 <= headContentHeight) {
						state = PULL_TO_REFRESH;
						changeOfHeadViewState();
					}
				}
				//关键:滑动的四个状态,刷新已经定死宽高和刷新完成由监听者执行,则只有下拉和松手刷新是没有定的,以下让其跟据滑动距离自动显示.
				if (state == PULL_TO_REFRESH || state == RELEASE_TO_REFRESH) {
					headView.setPadding(0,(int) ((temp - startY) / 3 - headContentHeight), 0,0);
				}
			}
			break;
			//松手时界面只有一种情况要处理:就是松手刷新提示转入刷新,其它都让headView进入不显示状态
		case MotionEvent.ACTION_UP:
			if (isRecord && state == RELEASE_TO_REFRESH) {
				state = REFRESHING;
				changeOfHeadViewState();
				onRefresh();
			} else {
				state = DONE;
				changeOfHeadViewState();
			}
			isRecord = false;//同时给标记false,让下次再按下时,重新记录新的startY值.
			break;
		}
		return super.onTouchEvent(ev);
	}
	//定义View各种状态的显示视图.
	private void changeOfHeadViewState() {
		switch (state) {
		case PULL_TO_REFRESH:// 下拉
			setHeadView(View.VISIBLE, View.GONE, "      下拉可以刷新");
			if (isBack) {
				arrow.startAnimation(animation);
				isBack = false;
			}
			break;
		case RELEASE_TO_REFRESH:// 松开刷新
			setHeadView(View.VISIBLE, View.GONE, "      松手可以刷新");
			arrow.startAnimation(reverseAnimation);
			break;
		case REFRESHING:// 正在刷新
			setHeadView(View.GONE, View.VISIBLE, "      正在加载....");
			headView.setPadding(0, 0, 0, 0);
			break;
		case DONE:// 刷新完成
			arrow.clearAnimation();
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			break;
		}
	}
	/**
	 * 设定headView的显示状态
	 * @param arrowVisibility  箭头是否可见
	 * @param refreshVisibility   刷新图标是否可见
	 * @param pullTVText 提示内容.
	 */
	private void setHeadView(int arrowVisibility, int refreshVisibility,
			String pullTVText) {
		arrow.setVisibility(arrowVisibility);
		refresh.setVisibility(refreshVisibility);
		pullTV.setVisibility(View.VISIBLE);
		pullTV.setText(pullTVText);
		timeTV.setVisibility(View.VISIBLE);
		arrow.clearAnimation();// 清除动画
	}
//定义刷新完成方法
	public void refreshCompletion() {
		state = DONE;
		changeOfHeadViewState();
		timeTV.setText("更新于:" + getCurrentTime());
	}
 // 定义获得当前时间方法
	public String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		Date date = new Date();
		String dateString = formatter.format(date);
		return dateString;
	}
//重定setAdapter让其在一显示页面时,就给页面设定一个当前时间.
	@Override
	public void setAdapter(ListAdapter adapter) {
		timeTV.setText("更新于:" + getCurrentTime());
		super.setAdapter(adapter);
	}
}
