android.view.Display的使用
类概述
Display类提供关于屏幕尺寸和分辨率的信息。

1.结构

继承关系

public class Display extends Object



2公共方法
//注意:这个类的方法是把获取的数据填充到参数对象中,而不像之前返回值.

//获取屏幕高度
public int getHeight ()

此方法不建议使用

请使用getSize(Point) 代替

//获取描述此屏幕尺寸和分辨率的DisplayMetrics对象。要先创建一个DisplayMetrics作为参数.
public void getMetrics (DisplayMetrics outMetrics)
此大小是基于当前屏幕旋转而调整的。
此方法返回的大小并不一定代表实际屏幕的原始大小（原始分辨率）。返回的大小可能会有所调整，以排除某些总是可见的系统布置元素。 
这也可能是被调整以提供与旧的为较小的初始的应用一致，初始应用最初是为较小的屏幕而设计的。
参数:outMetrics :一个 DisplayMetrics 对象接收 metrics.

//获得屏幕的方向
public int getOrientation ()
此方法不建议使用
请使用getRotation()代替
 返回值:屏幕的方向
 
 //返回本机屏幕像素格式。
public int getPixelFormat ()

返回本机屏幕像素格式。返回值可能是一个int类型的PixelFormat的常量。
返回:可能是一个int类型的PixelFormat的常量

//获取屏幕矩形的大小,以像素为单位. 要先创建一个矩形动画.
public void getRectSize (Rect outSize)
参数:OutSize    一个Rect对象接受大小的信息
参见
getSize(Point)

//返回屏幕帧每秒的刷新率。
public float getRefreshRate ()

//返回从“自然”(natural)方向的屏幕旋转度数。
public int getRotation ()

返回值可能Surface.ROTATION_0（不旋转），Surface.ROTATION_90，Surface.ROTATION_180，或Surface.ROTATION_270。
例如：如果设备有一个宽的屏幕，使用者转动进入横向，此时返回值可能是Surface.ROTATION_90 或者 Surface.ROTATION_270，取决于它旋转的方向。
角度是绘制图形在屏幕的旋转，这是设备物理旋转的相反方向。
例如：如果该设备是逆时针旋转90度，以弥补渲染会顺时针旋转90度，从而这里的返回值将会是Surface.ROTATION_90。

//获取屏幕的尺寸，以像素为单位。
public void getSize (Point outSize)
注意，此值不应被用于计算布局，因为一个设备沿着显示通常有屏幕装饰(例如状态栏)，减少的应用空间使得原尺寸与之不符。
布局应该改用窗口的大小。此大小是基于当前屏幕旋转而调整的。
此方法返回的大小并不一定代表实际屏幕的原始大小（原始分辨率）。
返回的大小可能会有所调整，以排除某些系统总是可见的装饰元素。这也可能是缩放，提供与旧，最初是为小屏幕设计的应用程序的兼容性。
参数:outsize    Point对象接收大小的信息。

//获取屏幕宽度.
public int getWidth ()
此方法不建议使用
请使用getSize(Point) 代替