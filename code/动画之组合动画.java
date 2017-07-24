//定义缩放动画 第一个参数表示x初始大小为两倍view,第二个参数表示缩放后的大小是原大小,第三个参数是y方向初始大小,
					//第四个参数是y缩放后的大小是原大小,最后两个参数是缩放时x和y方向的参照点,这里是view的中心点
	ScaleAnimation sa = new ScaleAnimation(0.2f, 1.0f, 0.2f,
			1.0f, 0.5f, 0.5f);
	sa.setDuration(600);

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
	view.startAnimation(set);
2.也可以在xml文件中定义,增强复用性

<?xml version="1.0" encoding="utf-8"?> 
<set xmlns:android="http://schemas.android.com/apk/res/android" 
    android:interpolator="@android:anim/accelerate_interpolator" 
    android:shareInterpolator="true" 
    > 
    <alpha 
        android:fromAlpha="1.0" 
        android:toAlpha="0.0" 
        android:startOffset="500" 
        android:duration="3000" 
            /> 
    <rotate 
        android:fromDegrees="0" 
        android:toDegrees="400" 
        android:pivotX="50%" 
        android:pivotY="50%" 
        android:duration="3000" 
    /> 
 
</set> 

<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/accelerate_interpolator"
    android:shareInterpolator="true"
    >
    <alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="500"
        android:duration="3000"
            />
    <rotate
        android:fromDegrees="0"
        android:toDegrees="400"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="3000"
    />

</set>