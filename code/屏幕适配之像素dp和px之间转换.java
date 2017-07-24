//为了让屏幕在代码中实现dip的效果,也就是在不同分遍率的手机上显示不同的大小,代码中是不能用dip的,只能用pix,那么就可以去定义一个dip去转换dip2px成pix,
//这就实现了不同屏幕上不同显示.如:
//后面两个参数需要pix,这定义成25dip去转,那么不同的屏幕就会转换成不同的pix.
holder.ll_container.addView(iv, DensityUtil.dip2px(getApplicationContext(), 25), DensityUtil.dip2px(getApplicationContext(), 25));
public class DensityUtil {  
  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}  