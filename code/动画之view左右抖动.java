//view左右抖动画
//1.在res中定义一个anim文件夹
//2.在文件夹中定义两个xml文件

//xml一
<?xml version="1.0" encoding="utf-8"?>

<cycleInterpolator xmlns:android="http://schemas.android.com/apk/res/android"
    android:cycles="7" />

//xml二
<?xml version="1.0" encoding="utf-8"?>

<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"
    android:fromXDelta="0"
    android:interpolator="@anim/cycle_7"
    android:toXDelta="10" />
	
//3.给view添加动画
Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
view.startAnimation(animation);// view表示一个view对象