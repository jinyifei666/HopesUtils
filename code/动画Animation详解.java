
 Animations

一、Animations介绍
Animations是一个实现android UI界面动画效果的API，Animations提供了一系列的动画效果，可以进行旋转、缩放、淡入淡出等，这些效果可以应用在绝大多数的控件中。 
二、Animations的分类
Animations从总体上可以分为两大类：
1.Tweened Animations：该类Animations提供了旋转、移动、伸展和淡出等效果。Alpha——淡入淡出，Scale——缩放效果，Rotate——旋转，Translate——移动效果。
2.Frame-by-frame Animations：这一类Animations可以创建一个Drawable序列，这些Drawable可以按照指定的时间间歇一个一个的显示。
 
三、Animations的使用方法（代码中使用）
Animations extends Object implements Cloneable 
 使用TweenedAnimations的步骤：
1.创建一个AnimationSet对象（Animation子类）；
2.增加需要创建相应的Animation对象；
3.更加项目的需求，为Animation对象设置相应的数据；
4.将Animatin对象添加到AnimationSet对象当中；
5.使用控件对象开始执行AnimationSet。

　　Tweened Animations的分类
　　１、Alpha：淡入淡出效果
　　２、Scale：缩放效果
　　３、Rotate：旋转效果
　　４、Translate：移动效果
 
Animation的四个子类：
　　AlphaAnimation、TranslateAnimation、ScaleAnimation、RotateAnimation
四、具体实现
1、main.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >
 
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal" >
         <Button
            android:id="@+id/rotateButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="旋转" />
         <Button
            android:id="@+id/scaleButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="缩放" />
         <Button
            android:id="@+id/alphaButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="淡入淡出" />
         <Button
            android:id="@+id/translateButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="移动" />
    </LinearLayout>
     <LinearLayout
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:orientation="vertical" >
         <ImageView
            android:id="@+id/image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:src="@drawable/an" />
    </LinearLayout>
 </LinearLayout>
2、.java文件
importandroid.app.Activity;
importandroid.os.Bundle;
importandroid.view.View;
importandroid.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
importandroid.view.animation.AnimationSet;
importandroid.view.animation.RotateAnimation;
importandroid.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
importandroid.widget.Button;
importandroid.widget.ImageView;
public class Animation1Activity extends Activity {
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        rotateButton = (Button)findViewById(R.id.rotateButton);
        scaleButton = (Button)findViewById(R.id.scaleButton);
        alphaButton = (Button)findViewById(R.id.alphaButton);
        translateButton = (Button)findViewById(R.id.translateButton);
        image = (ImageView)findViewById(R.id.image);
     
        rotateButton.setOnClickListener(newRotateButtonListener());
        scaleButton.setOnClickListener(newScaleButtonListener());
        alphaButton.setOnClickListener(newAlphaButtonListener());
        translateButton.setOnClickListener(
           new TranslateButtonListener());
    }
    class AlphaButtonListener implementsOnClickListener{
       public void onClick(View v) {
           //创建一个AnimationSet对象，参数为Boolean型，
           //true表示使用Animation的interpolator，false则是使用自己的
           AnimationSet animationSet = new AnimationSet(true);
           //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
           AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
           //设置动画执行的时间
           alphaAnimation.setDuration(500);
           //将alphaAnimation对象添加到AnimationSet当中
           animationSet.addAnimation(alphaAnimation);
           //使用ImageView的startAnimation方法执行动画
           image.startAnimation(animationSet);
       }
    }
    class RotateButtonListener implementsOnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //参数1：从哪个旋转角度开始
           //参数2：转到什么角度
           //后4个参数用于设置围绕着旋转的圆的圆心在哪里
           //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
           //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
           //参数5：确定y轴坐标的类型
           //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
           RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           rotateAnimation.setDuration(1000);
           animationSet.addAnimation(rotateAnimation);
           image.startAnimation(animationSet);
       }
    }
    class ScaleButtonListener implementsOnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //参数1：x轴的初始值
           //参数2：x轴收缩后的值
           //参数3：y轴的初始值
           //参数4：y轴收缩后的值
           //参数5：确定x轴坐标的类型
           //参数6：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
           //参数7：确定y轴坐标的类型
           //参数8：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
           ScaleAnimation scaleAnimation = new ScaleAnimation(
                  0, 0.1f,0,0.1f,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           scaleAnimation.setDuration(1000);
           animationSet.addAnimation(scaleAnimation);
           image.startAnimation(animationSet);
       }
    }
    class TranslateButtonListener implementsOnClickListener{
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           //参数1～2：x轴的开始位置
           //参数3～4：y轴的开始位置
           //参数5～6：x轴的结束位置
           //参数7～8：x轴的结束位置
           TranslateAnimation translateAnimation =
              new TranslateAnimation(
                  Animation.RELATIVE_TO_SELF,0f,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           translateAnimation.setDuration(1000);
           animationSet.addAnimation(translateAnimation);
           image.startAnimation(animationSet);
       }
    }
}
 
Tween Animations的通用方法
　　１、setDuration(long durationMills)
　　设置动画持续时间（单位：毫秒）
　　２、setFillAfter(Boolean fillAfter)
　　如果fillAfter的值为true,则动画执行后，控件将停留在执行结束的状态
　　３、setFillBefore(Boolean fillBefore)
　　如果fillBefore的值为true，则动画执行后，控件将回到动画执行之前的状态
　　４、setStartOffSet(long startOffSet)
　　设置动画执行之前的等待时间
　　５、setRepeatCount(int repeatCount)
　　设置动画重复执行的次数
 
在代码中使用Animations可以很方便的调试、运行，但是代码的可重用性差，重复代码多。同样可以在xml文件中配置Animations，这样做可维护性变高了，只不过不容易进行调试。
一、在xml中使用Animations步骤
       1.在res文件夹下建立一个anim文件夹；
       2.创建xml文件，并首先加入set标签，更改标签如下：
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator">
</set>
3.在该标签当中加入rotate，alpha，scale或者translate标签；
<alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="500"
        android:duration="500"/>
4.在代码当中使用AnimationUtils当中装载xml文件，并生成Animation对象。因为Animation是AnimationSet的子类，所以向上转型，用Animation对象接收。
Animation animation = AnimationUtils.loadAnimation(
                  Animation1Activity.this, R.anim.alpha);
           // 启动动画
           image.startAnimation(animation);
二、具体实现
 1、alpha.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator">
    <!-- fromAlpha和toAlpha是起始透明度和结束时透明度 -->
    <alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="500"
        android:duration="500"/>
</set>
2、rotate.xml
 
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator">
    <!--
        fromDegrees:开始的角度
        toDegrees：结束的角度，+表示是正的
        pivotX：用于设置旋转时的x轴坐标
        例
           1)当值为"50"，表示使用绝对位置定位
           2)当值为"50%"，表示使用相对于控件本身定位
           3)当值为"50%p"，表示使用相对于控件的父控件定位
        pivotY：用于设置旋转时的y轴坐标
      -->
    <rotate
        android:fromDegrees="0"
        android:toDegrees="+360"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="1000"/>
</set>
3、scale.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator">
   <!--
       起始x轴坐标
           止x轴坐标
           始y轴坐标
           止y轴坐标
           轴的坐标
           轴的坐标
     -->
   <scale
       android:fromXScale="1.0"
       android:toXScale="0.0"
       android:fromYScale="1.0"
       android:toYScale="0.0"
       android:pivotX="50%"
       android:pivotY="50%"
       android:duration="1000"/>
</set>
 
4、translate.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator">
    <!--
           始x轴坐标
           止x轴坐标
           始y轴坐标
           止y轴坐标
      -->
    <translate
        android:fromXDelta="0%"
        android:toXDelta="100%"
        android:fromYDelta="0%"
        android:toYDelta="100%"
        android:duration="2000"/>
</set>
 
5、.java文件
importandroid.app.Activity;
importandroid.os.Bundle;
importandroid.view.View;
importandroid.view.View.OnClickListener;
import android.view.animation.Animation;
importandroid.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
 
public class Animation1Activity extends Activity {
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
 
       rotateButton = (Button) findViewById(R.id.rotateButton);
       scaleButton = (Button) findViewById(R.id.scaleButton);
       alphaButton = (Button) findViewById(R.id.alphaButton);
       translateButton = (Button) findViewById(R.id.translateButton);
       image = (ImageView) findViewById(R.id.image);
 
       rotateButton.setOnClickListener(newRotateButtonListener());
       scaleButton.setOnClickListener(newScaleButtonListener());
       alphaButton.setOnClickListener(newAlphaButtonListener());
       translateButton.setOnClickListener(newTranslateButtonListener());
    }
 
    class AlphaButtonListener implementsOnClickListener {
       public void onClick(View v) {
           // 使用AnimationUtils装载动画配置文件
           Animation animation = AnimationUtils.loadAnimation(
                  Animation1Activity.this, R.anim.alpha);
           // 启动动画
           image.startAnimation(animation);
       }
    }
 
    class RotateButtonListener implementsOnClickListener {
       public void onClick(View v) {
           Animation animation = AnimationUtils.loadAnimation(
                  Animation1Activity.this, R.anim.rotate);
           image.startAnimation(animation);
       }
    }
 
    class ScaleButtonListener implementsOnClickListener {
       public void onClick(View v) {
           Animation animation = AnimationUtils.loadAnimation(
                  Animation1Activity.this, R.anim.scale);
           image.startAnimation(animation);
       }
    }
 
    class TranslateButtonListener implementsOnClickListener {
       public void onClick(View v) {
           Animation animation = AnimationUtils.loadAnimation(Animation1Activity.this, R.anim.translate);
           image.startAnimation(animation);
       }
    }
}
 
AnimationSet的具体使用方法
 
       1.AnimationSet是Animation的子类；
 
       2.一个AnimationSet包含了一系列的Animation；
 
       3.针对AnimationSet设置一些Animation的常见属性（如startOffset，duration等），可以被包含在AnimationSet当中的Animation集成；
 
例：一个AnimationSet中有两个Animation，效果叠加
 
第一种方法：
 
doubleani.xml
 
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator"
    android:shareInterpolator="true">
    <!-- fromAlpha和toAlpha是起始透明度和结束时透明度 -->
    <alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="500"
        android:duration="500"/>
    <translate
        android:fromXDelta="0%"
        android:toXDelta="100%"
        android:fromYDelta="0%"
        android:toYDelta="100%"
        android:duration="2000"/>
</set>
 
.java文件中
 
classDoubleButtonListener implements OnClickListener {
       public void onClick(View v) {
           // 使用AnimationUtils装载动画配置文件
           Animation animation = AnimationUtils.loadAnimation(
                  Animation2Activity.this, R.anim. doubleani);
           // 启动动画
           image.startAnimation(animation);
       }
    }
 
第二种方法：
 
.java文件中
 
classDoubleButtonListener implements OnClickListener {
       public void onClick(View v) {
           AnimationSet animationSet = new AnimationSet(true);
           AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
           RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                  Animation.RELATIVE_TO_SELF,0.5f,
                  Animation.RELATIVE_TO_SELF,0.5f);
           rotateAnimation.setDuration(1000);
           animationSet.addAnimation(rotateAnimation);
           animationSet.addAnimation(alphaAnimation);
           image.startAnimation(animationSet);
 
       }
    }
 
Interpolator的具体使用方法
 
       Interpolator定义了动画变化的速率，在Animations框架当中定义了一下几种Interpolator
?         AccelerateDecelerateInterpolator：在动画开始与结束的地方速率改变比较慢，在中间的时候速率快。
?         AccelerateInterpolator：在动画开始的地方速率改变比较慢，然后开始加速
?         CycleInterpolator：动画循环播放特定的次数，速率改变沿着正弦曲线
?         DecelerateInterpolator：在动画开始的地方速率改变比较慢，然后开始减速
?         LinearInterpolator：动画以均匀的速率改变
分为以下几种情况：
1、在set标签中
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator"/>
2、如果在一个set标签中包含多个动画效果，如果想让这些动画效果共享一个Interpolator。
    android:shareInterpolator="true"
3、如果不想共享一个interpolator，则设置android:shareInterpolator="true"，并且需要在每一个动画效果处添加interpolator。
<alpha
        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="500"
        android:duration="500"/>
 
4、如果是在代码上设置共享一个interpolator，则可以在AnimationSet设置interpolator。
 
AnimationSet animationSet = newAnimationSet(true);
animationSet.setInterpolator(new AccelerateInterpolator());
 
5、如果不设置共享一个interpolator则可以在每一个Animation对象上面设置interpolator。
 
AnimationSet animationSet = newAnimationSet(false);
alphaAnimation.setInterpolator(new AccelerateInterpolator());
rotateAnimation.setInterpolator(new DecelerateInterpolator());
 
Frame-By-Frame Animations的使用方法
 
       Frame-By-Frame Animations是一帧一帧的格式显示动画效果。类似于电影胶片拍摄的手法。
 main.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <LinearLayout
        android:orientation="horizontal"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content">
       <Button
           android:id="@+id/button"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="运动"/>
    </LinearLayout>
    <LinearLayout
        android:orientation="vertical"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">
       <ImageView
           android:id="@+id/image"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"/>
    </LinearLayout>
</LinearLayout>
 
3、anim.xml
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false">
    <item android:drawable="@drawable/a_01" android:duration="50"/>
    <item android:drawable="@drawable/a_02" android:duration="50"/>
    <item android:drawable="@drawable/a_03" android:duration="50"/>
    <item android:drawable="@drawable/a_04" android:duration="50"/>
    <item android:drawable="@drawable/a_05" android:duration="50"/>
    <item android:drawable="@drawable/a_06" android:duration="50"/>
</animation-list>
 
4、.java文件
importandroid.app.Activity;
importandroid.graphics.drawable.AnimationDrawable;
importandroid.os.Bundle;
importandroid.view.View;
importandroid.view.View.OnClickListener;
importandroid.widget.Button;
importandroid.widget.ImageView;
public class AnimationsActivity extends Activity {
    private Button button = null;
    private ImageView imageView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.image);
        button.setOnClickListener(newButtonListener());
    }
    class ButtonListener implementsOnClickListener{
       public void onClick(View v) {
           imageView.setBackgroundResource(R.anim.anim);
           AnimationDrawable animationDrawable = (AnimationDrawable)
              imageView.getBackground();
           animationDrawable.start();
       }
    }
}
 
   LayoutAnimationsController
1、什么是LayoutAnimationsController
LayoutAnimationsController可以用于实现使多个控件按顺序一个一个的显示。
1)LayoutAnimationsController用于为一个layout里面的控件，或者是一个ViewGroup里面的控件设置统一的动画效果。
2)每一个控件都有相同的动画效果。
3)控件的动画效果可以在不同的时间显示出来。
4)LayoutAnimationsController可以在xml文件当中设置，以可以在代码当中进行设置。
2、在xml当中使用LayoutAnimationController
1)在res/anim文件夹下创建一个名为list_anim_layout.xml文件：
android:delay - 动画间隔时间；子类动画时间间隔 （延迟）   70% 也可以是一个浮点数 如“1.2”等
android:animationOrder - 动画执行的循序(normal：顺序，random：随机，reverse：反向显示)
android:animation – 引用动画效果文件
<layoutAnimation
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:delay="0.5"
    android:animationOrder="normal"
    android:animation="@anim/list_anim"/>
2)创建list_anim.xml文件，设置动画效果
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator"
    android:shareInterpolator="true">
    <alpha
       android:fromAlpha="0.0"
       android:toAlpha="1.0"
       android:duration="1000"/>
</set>
 
3）在布局文件main.xml当中为ListVIew添加如下配置
<ListView
       android:id="@id/android:list"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:scrollbars="vertical"
        android:layoutAnimation="@anim/list_anim_layout"/>
4)程序结构
 


 
 
5)list_anim_layout.xml
<layoutAnimation
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:delay="0.5"
    android:animationOrder="normal"
    android:animation="@anim/list_anim"/>
6)list_anim.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator"
    android:shareInterpolator="true">
    <alpha
       android:fromAlpha="0.0"
       android:toAlpha="1.0"
       android:duration="1000"/>
</set>
 
7)main.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    >
    <ListView
       android:id="@id/android:list"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:scrollbars="vertical"
        android:layoutAnimation="@anim/list_anim_layout"/>
    <Button
        android:id="@+id/button"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="测试"/>
</LinearLayout>
8)item.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="horizontal"
    android:paddingLeft="10dip"
    android:paddingRight="10dip"
    android:paddingTop="1dip"
    android:paddingBottom="1dip">
    <TextView android:id="@+id/name"
       android:layout_width="180dip"
       android:layout_height="30dip"
       android:textSize="5pt"
       android:singleLine="true" />
    <TextView android:id="@+id/sex"
       android:layout_width="fill_parent"
       android:layout_height="fill_parent"
       android:textSize="5pt"
       android:singleLine="true"/>
</LinearLayout>
9)java文件
public class Animation2Activity extendsListActivity {
    private Button button = null;
    private ListView listView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = getListView();
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(newButtonListener());
    }
    private ListAdapter createListAdapter() {
       List<HashMap<String,String>> list =
           new ArrayList<HashMap<String,String>>();
       HashMap<String,String> m1 = new HashMap<String,String>();
       m1.put("name", "bauble");
       m1.put("sex", "male");
       HashMap<String,String> m2 = new HashMap<String,String>();
       m2.put("name", "Allorry");
       m2.put("sex", "male");
       HashMap<String,String> m3 = new HashMap<String,String>();
       m3.put("name", "Allotory");
       m3.put("sex", "male");
       HashMap<String,String> m4 = new HashMap<String,String>();
       m4.put("name", "boolbe");
       m4.put("sex", "male");
       list.add(m1);
       list.add(m2);
       list.add(m3);
       list.add(m4);
       SimpleAdapter simpleAdapter = new SimpleAdapter(
              this,list,R.layout.item,new String[]{"name","sex"},
              new int[]{R.id.name,R.id.sex});
       return simpleAdapter;
    }
    private class ButtonListener implementsOnClickListener{
       public void onClick(View v) {
           listView.setAdapter(createListAdapter());
       }
    }
}
 
备注：要将整个动画效果设置到LinerLayout中，可以这样设置：
<LinearLayoutxmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:layoutAnimation="@anim/list_anim_layout"
> 
 
3、在代码当中使用LayoutAnimationController
1）去掉main.xml中的android:layoutAnimation="@anim/list_anim_layout"/>
2）创建一个Animation对象：可以通过装载xml文件，或者是直接使用Animation的构造方法创建Animation对象；
Animation animation = (Animation) AnimationUtils.loadAnimation(
                  Animation2Activity.this, R.anim.list_anim);
3）创建LayoutAnimationController对象：  
LayoutAnimationController controller = new LayoutAnimationController(animation); 
            
4）设置控件的显示顺序以及延迟时间
controller.setOrder(LayoutAnimationController.ORDER_NORMAL); 
controller.setDelay(0.5f);        
5）为ListView设置LayoutAnimationController属性：
listView.setLayoutAnimation(controller);
完整代码：
private class ButtonListener implementsOnClickListener {
       public void onClick(View v) {
           listView.setAdapter(createListAdapter());
           Animation animation = (Animation) AnimationUtils.loadAnimation(
                  Animation2Activity.this, R.anim.list_anim);
          
           LayoutAnimationController controller = new LayoutAnimationController(animation); 
           controller.setOrder(LayoutAnimationController.ORDER_NORMAL); 
           controller.setDelay(0.5f);
           listView.setLayoutAnimation(controller); 
       }
    }
 
 AnimationListener
1、什么是AnimationListener
1).AnimationListener是一个监听器，该监听器在动画执行的各个阶段会得到通知，从而调用相应的方法；
2).AnimationListener主要包括如下三个方法：
n         ·onAnimationEnd(Animation animation) - 当动画结束时调用
n         ·onAnimationRepeat(Animation animation) - 当动画重复时调用
n         ·onAniamtionStart(Animation animation) - 当动画启动时调用
2、具体实现
1）main.xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/layout"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <Button android:id="@+id/addButton"
       android:layout_width="fill_parent"
       android:layout_height="wrap_content"
       android:layout_alignParentBottom="true"
       android:text="添加图片" />
    <Button android:id="@+id/deleteButton"
       android:layout_width="fill_parent"
       android:layout_height="wrap_content"
       android:layout_above="@id/addButton"
       android:text="删除图片" />
    <ImageView android:id="@+id/image"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_centerInParent="true"
       android:layout_marginTop="100dip"
       android:src="@drawable/an" />
</RelativeLayout>
2).java文件
public class Animation2Activity extends Activity {
    private Button addButton = null;
    private Button deleteButton = null;
    private ImageView imageView = null;
    private ViewGroup viewGroup = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addButton = (Button)findViewById(R.id.addButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        imageView = (ImageView)findViewById(R.id.image);
        //LinearLayout下的一组控件
        viewGroup = (ViewGroup)findViewById(R.id.layout);
        addButton.setOnClickListener(newAddButtonListener());
        deleteButton.setOnClickListener(newDeleteButtonListener());
    }
    private class AddButtonListener implements OnClickListener{
       public void onClick(View v) {
           //淡入
           AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
           animation.setDuration(1000);
           animation.setStartOffset(500);
           //创建一个新的ImageView
           ImageView newImageView = new ImageView(
              Animation2Activity.this);
           newImageView.setImageResource(R.drawable.an);
           viewGroup.addView(newImageView,
              new LayoutParams(
                  LayoutParams.FILL_PARENT,
                  LayoutParams.WRAP_CONTENT));
           newImageView.startAnimation(animation);
       }
    }
    private class DeleteButtonListener implements OnClickListener{
       public void onClick(View v) {
           //淡出
           AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
           animation.setDuration(1000);
           animation.setStartOffset(500);
           //为Aniamtion对象设置监听器
           animation.setAnimationListener(
              new RemoveAnimationListener());
           imageView.startAnimation(animation);
       }
    }
    private class RemoveAnimationListener implements AnimationListener{
       //动画效果执行完时remove
       public void onAnimationEnd(Animation animation) {
           System.out.println("onAnimationEnd");
           viewGroup.removeView(imageView);
       }
       public void onAnimationRepeat(Animation animation) {
           System.out.println("onAnimationRepeat");
       }
       public void onAnimationStart(Animation animation) {
           System.out.println("onAnimationStart");
       }
    }
}
3、总结一下
可以在Activity中动态添加和删除控件，方法是：
1)取到那个Layout
viewGroup = (ViewGroup)findViewById(R.id.layout);
2)添加时，先创建对象，然后添加
ImageView newImageView = new ImageView(
              Animation2Activity.this);
newImageView.setImageResource(R.drawable.an);
viewGroup.addView(newImageView,
              new LayoutParams(
                  LayoutParams.FILL_PARENT,
                  LayoutParams.WRAP_CONTENT));
3）删除时，直接删除。
viewGroup.removeView(imageView);