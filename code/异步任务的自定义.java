
import android.os.Handler;
//自定义一个异步任务
public abstract class MyAsyncTask {
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			onPostExecute();
		};
	};
	/**
	 * 1.耗时任务开启之前调用的方法.
	 */
	public abstract void onPreExecute();

	/**
	 * 3. 耗时任务执行之后调用的方法
	 */
	public abstract void onPostExecute();
	
	/**
	 * 在后台执行的耗时的任务 运行在子线程.
	 */
	public abstract void doInBackground();

	/**
	 * 执行一个异步任务.
	 */
	public void execute() {
		onPreExecute();
		new Thread() {
			public void run() {
				doInBackground();
				handler.sendEmptyMessage(0);
			};
		}.start();

	}
}
