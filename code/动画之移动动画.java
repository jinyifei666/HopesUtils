
1.动画之左右移动
//TranslateAnimation是移动的动画效果。它有三个构造函数，分别是：

	1.public　TranslateAnimation(Context context,AttributeSet attrs)   略过

	2.public　TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)

	　　这个是我们最常用的一个构造方法，

	　　float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；

	　　float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；

	　　float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；

	　　float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；

	　　如果view在A(x,y)点 那么动画就是从B点(x+fromXDelta, y+fromYDelta)点移动到C 点(x+toXDelta,y+toYDelta)点.

	3.public　TranslateAnimation (int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)

	　　fromXType:第一个参数是x轴方向的值的参照(Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF,or Animation.RELATIVE_TO_PARENT);

	　　fromXValue:第二个参数是第一个参数类型的起始值；

	　　toXType,toXValue:第三个参数与第四个参数是x轴方向的终点参照与对应值；

后面四个参数就不用解释了。如果全部选择Animation.ABSOLUTE，其实就是第二个构造函数。

以x轴为例介绍参照与对应值的关系:

//如果选择参照为Animation.ABSOLUTE，那么对应的值应该是具体的坐标值，比如100到300，指绝对的屏幕像素单位
//如果选择参照为Animation.RELATIVE_TO_SELF或者 Animation.RELATIVE_TO_PARENT指的是相对于自身或父控件，对应值应该理解为相对于自身或者父控件的几倍或百分之多少
1.注:1.0表示父view的全部,float类型,所以写1.0f.
//代码事例:
	//1.向右平移至消失.
		TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 1.0f,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0);
		ta.setDuration(800);//动画执行时间
		view.startAnimation(ta);//启动动画.
	//1.向左平移至消失.
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0,//表示以0为启动点
				Animation.RELATIVE_TO_SELF, -1.0f,//表示启始点移动到了最左边消失不见
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0);
		ta.setDuration(800);
		view.startAnimation(ta);