//标题栏的去除
//在manifest中定义
<application
        android:icon="@drawable/shenmatran"
        android:theme="@android:style/Theme.Light.NoTitleBar"//用于Application表示每个Activity都没有标题栏,只能有一个theme
        android:label="@string/app_name" >
        <activity
            android:theme="@android:style/Theme.NoTitleBar.Fullscreen">//表示这个Activity可以有标题栏
在Activity中
requestWindowFeature(Window.FEATURE_NO_TITLE);//表示这个Activity无标题
Activity的属性:
<activity android:name=".NewMessageActivity" android:label="@string/new_msg" />activity的属性之中label是activity对外显示的名字
//让一个CheckBox不可被点击,不能获得焦点
<CheckBox
        android:clickable="false"//不可被点击
        android:focusable="false"//不能获得焦点
		......
...../>

2.ListView中:

android:cacheColorHint="#00000000" 让点击不会有黑色
android:listSelector="@anim/shape_rounded_selectable"  定义背景选择器
GridView:
在xml里有这个属性可以设置 
android:horizontalSpacing
两列之间的间距。
android:numColumns
设置列数。
android:stretchMode
缩放模式。
android:verticalSpacing
两行之间的间距。