public class ActivityUtils {
	/**
	 * 开启新的activity 并且关闭掉自己
	 */
	public static void startActivityAndFinish(Activity context, Class<?> cls){
		Intent intent = new Intent(context,cls);
		context.startActivity(intent);
		context.finish();
	}
	
	
	/**
	 * 开启新的activity 
	 */
	public static void startActivity(Activity context, Class<?> cls){
		Intent intent = new Intent(context,cls);
		context.startActivity(intent);
	}
}
