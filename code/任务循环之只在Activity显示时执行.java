/**
 * 在Activity中循环执行一个任务,切换到其它Activity后取消的方法,回到Activity又继续执行.
 * 利用onStart与onStop方法
 * 两个关健点:
 * 	onstart第一次时要进行非空判断
 *  线程run执行完了,线程就中止了,所以用一个flag开关去控制run的循环.
 * @author fada
 *
 */
public class AutoAndPauseTask extends Activity {
	private boolean flag;
	private String s;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				System.out.println("我收到消息了");

			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		s = "可以执行了";
		new MyTask().start();

	};

	class MyTask extends Thread {
		@Override
		public void run() {
			while (flag) {
				SystemClock.sleep(2000);// 间隔两秒执行
				System.out.println("我执行了");
				handler.sendEmptyMessage(0);
			}
		}
	}

	@Override
	protected void onStart() {
		flag = true;
		// 第一次执行前作判断,避免空指针,
		if (null != s) {
			new MyTask().start();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		flag = false;
		super.onStop();
	}
}