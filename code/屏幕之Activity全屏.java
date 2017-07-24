方式一:manifest中定义
 <activity
 ...........
 android:theme="@android:style/Theme.NoTitleBar.Fullscreen"//这个属性表示没标题且全屏
  android:theme="@android:style/Theme.NoTitleBar  //这个属性表示属性,只没有标题
 >
方式二:把这段代码放于onCreate中,则这个Activity会全屏
public BaseActivity extends Activity{
	@Override
	public void onCreate(Bundle paramBundle) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
}
