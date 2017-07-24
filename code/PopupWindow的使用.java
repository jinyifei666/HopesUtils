1.PopupWindow的使用
//注意:
//1.需要强调的是这里PopupWindow必须有某个事件触发才会显示出来不然总会抱错，
//2.如果想初始化就让PopupWindow显示出来,就用了定时器Timer来实现这样的效果,当然这里就要用到Handler了



//常见应用
1.定义一个占满全屏的popwindow让屏目暂时不可以点击.

public class AppLockActivity extends Activity implements OnClickListener {
	private PopupWindow popwindow;	
	private ListView lv_unlock, lv_locked;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Display类提供关于屏幕尺寸和分辨率的信息。
		Display display = getWindowManager().getDefaultDisplay();
		//获取一个与整个屏幕的宽高一样的popwindow对象
		popwindow = new PopupWindow(new View(this), display.getWidth(), display.getHeight());		
		//在一个界面条目的点击事件是显示出来
		lv_unlock.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,//这个view表法点击的条目
					int position, long id) {
				//设置popwindow背景为透明
				popwindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				//显示popwindow在相对于Gravity.TOP|Gravity.LEFT(表示左上角)的0,0位置上.
				popwindow.showAtLocation(parent, Gravity.TOP|Gravity.LEFT, 0, 0);
				//这是个自定义的延期执行工具类,表示延期800毫秒关闭popwindow
				new DelayExecuter() {
					@Override
					public void onPostExecute() {
						popwindow.dismiss();
					}
				}.execute(800);
			}
		});
	}
}
2.定义点击view后弹出一个带动画的popupwindow
public class AppLockActivity extends Activity implements OnClickListener {
	private PopupWindow popwindow;	
	private ListView lv_app_manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		lv_app_manager.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// 在点击操作里面判断 如果当前界面已经存在了弹出窗体 先去关闭他.
				dismissPopupWindow();
				// 根据position去获取被点击条目对应的对象.
				Object obj = lv_app_manager.getItemAtPosition(position);
				if (obj instanceof AppInfo) {
					selectedAppInfo = (AppInfo) obj;
					Logger.i(TAG, "packname:" + selectedAppInfo.getPackname());
					View contentView = View.inflate(getApplicationContext(),R.layout.ui_popupwindow_app, null);
					//定义PopupWindow的view中里面的子view和点击事件
					ll_share = (LinearLayout) contentView.findViewById(R.id.ll_share);
					ll_start = (LinearLayout) contentView.findViewById(R.id.ll_start);
					ll_uninstall = (LinearLayout) contentView.findViewById(R.id.ll_uninstall);
					ll_share.setOnClickListener(AppManagerActivity.this);
					ll_start.setOnClickListener(AppManagerActivity.this);
					ll_uninstall.setOnClickListener(AppManagerActivity.this);
					//创建一个PopupWindow对象
					popwindow = new PopupWindow(contentView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
					//定义背景色
					popwindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					int[] location = new int[2];//定义一个int型数组,用于存放x和y的坐标
					view.getLocationInWindow(location);//获得父view的坐标,填入int数组中
					popwindow.showAtLocation(parent,Gravity.TOP | Gravity.LEFT//表示参照点
												, location[0] + 60//让x轴方法向右偏移60个dip
												,location[1]);//y轴方向与父view一样
					//定义缩放动画 第一个参数表示x初始大小为两倍view,第二个参数表示缩放后的大小是原大小,第三个参数是y方向初始大小,
					//第四个参数是y缩放后的大小是原大小,最后两个参数是缩放时x和y方向的参照点,这里是view的中心点
					ScaleAnimation sa = new ScaleAnimation(0.2f, 1.0f, 0.2f,1.0f, 0.5f, 0.5f);//定义缩放动画
					sa.setDuration(600);
					//定义平移动画,x从0位出现至全部,y方向保持不变.
					TranslateAnimation ta = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 0.1f,
							Animation.RELATIVE_TO_SELF, 0,
							Animation.RELATIVE_TO_SELF, 0);
					ta.setDuration(800);
					//AnimationSet可以加入Animation，加入之后设置AnimationSet对加入的所有Animation都有效。
					//传入true那么所有的Animation都用AnimationSet的Interpolator(动画变化的速率.)false表示用每个动画自已的Interpolator.
					AnimationSet set = new AnimationSet(false);
					set.addAnimation(sa);
					set.addAnimation(ta);
					contentView.startAnimation(set);
					contentView.clearAnimation();
				}
			}
		});
	}
}