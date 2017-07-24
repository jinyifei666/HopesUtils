//自定义组合控件: 作用是:把一个应用中相似的一套组件抽取出来,形成一个组合控件,方便在其它的view中引用.
//首先,定义好一个组合的layout.xml
//比如: 
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="64dip" >

    <TextView
        android:id="@+id/tv_settingview_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="6dip"
        android:layout_marginTop="5dip"
        android:text="设置的标题"
        android:textAppearance="?android:attr/textAppearanceLarge" />

    <TextView
        android:id="@+id/tv_settingview_content"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_below="@+id/tv_settingview_title"
        android:layout_marginLeft="7dip"
        android:layout_marginTop="5dip"
        android:text="TextView" />

    <CheckBox
        android:clickable="false"
        android:focusable="false"
        android:id="@+id/checkBox1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_centerVertical="true" />

    <View
        android:layout_alignParentBottom="true"
        android:background="@drawable/listview_devider"
        android:layout_width="fill_parent"
        android:layout_height="1dip" >
    </View>

</RelativeLayout>

//现在要实现的效果就是把这个封装成一个自定义view,到时候可以在其它layout中引用就可以了
//比如:在其它layout中直接引入这个就可以.
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:itheima="http://schemas.android.com/apk/res/cn.itheima.mobilesafe"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >
    <TextView
        android:layout_width="fill_parent"
        android:layout_height="45dip"
        android:background="#355E9E"
        android:gravity="center"
        android:text="设置中心"
        android:textColor="#EBC950"
        android:textSize="23dip" />
    <cn.itheima.mobilesafe.ui.SettingView
        android:id="@+id/sv_setting_update"
        itheima:title="自动更新设置"
        itheima:checked_text="自动更新已经开启"
        itheima:unchecked_text="自动更新没有开启"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content" >
    </cn.itheima.mobilesafe.ui.SettingView>
	
//控件中还可以定义一些属性,如:一些字符串,到时候在自定义view的类中就可以直接引入获得,然后关联到组合控件中的需要的view中.
//这样到时候,只在引用组合控件时,定义不同的属性,则就可以让组合控件中的view作不同的数据或状态显示.
//要实现则要在values中把需要的属性自定义成一个xml  values目录 创建declare-styleable  自定义的属性.如下:
<?xml version="1.0" encoding="utf-8"?>
<resources>
     <declare-styleable name="setting_view_style">//name表示这个资源的名称
//name表示属性名 format表示属性值类型.可以是String ,color等,可以参照:\android-sdk\platforms\android-10\data\res\values下的attrs.xml
         <attr name="title" format="string"></attr>
         <attr name="checked_text" format="string"></attr>
         <attr name="unchecked_text" format="string"></attr>
     </declare-styleable>
</resources>


</LinearLayout>
	
//接下来就要自定义这个组合控件的类了,步骤如下:
//1. 写一个类 继承ViewGroup 
public class SettingView extends RelativeLayout {
	private View view;
	private TextView tv_settingview_title;
	private TextView tv_settingview_content;
	private CheckBox cb_status;
	private String checked_text;
	private String unchecked_text;
	
//2.定义一个初始化方法,加载要定义的那个layout及里面的view,获得对象
	private void initView(Context context) {
		view = View.inflate(context, R.layout.ui_setting_view, this);//先加载
		tv_settingview_title = (TextView) view.findViewById(R.id.tv_settingview_title);
		tv_settingview_content = (TextView) view.findViewById(R.id.tv_settingview_content);
		cb_status = (CheckBox) view.findViewById(R.id.checkBox1);
	}
//3.重写构造方法把初始化方法定义到每一个方法.
	public SettingView(Context context) {
		super(context);
		initView(context);
	}
	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
//4.在这个方法中把组合控件中view的属性和我们自己定义的属性集合建立映射关系,
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		//先获得属性集合
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.setting_view_style);
		//获得每一个属性
		String title = a.getString(R.styleable.setting_view_style_title);
		checked_text = a.getString(R.styleable.setting_view_style_checked_text);
		unchecked_text = a.getString(R.styleable.setting_view_style_unchecked_text);
		//将获得的属性关联到view的属性中
		tv_settingview_content.setText(unchecked_text);
		tv_settingview_title.setText(title);
		a.recycle();//释放资源.
	}

}
//最后就可以向上面一样引用了
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:itheima="http://schemas.android.com/apk/res/cn.itheima.mobilesafe"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >
    <TextView
        android:layout_width="fill_parent"
        android:layout_height="45dip"
        android:background="#355E9E"
        android:gravity="center"
        android:text="设置中心"
        android:textColor="#EBC950"
        android:textSize="23dip" />
    <cn.itheima.mobilesafe.ui.SettingView
        android:id="@+id/sv_setting_update"
        itheima:title="自动更新设置"
        itheima:checked_text="自动更新已经开启"
        itheima:unchecked_text="自动更新没有开启"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content" >
    </cn.itheima.mobilesafe.ui.SettingView>