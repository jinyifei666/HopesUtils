/**
 * 用于执行定时任务,及任务执行后作的处理.可以暂停和重新开始
 * @author fada
 *
 */
public abstract class AutoTask {
	private ScheduledExecutorService  ses;
	boolean flag;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if (msg.what==8) {
				afterTask();
			}
		}
	};
	/**
	 * 定时执行的任务后需要执行的动作
	 */
	public abstract void afterTask();
	/**
	 * 定时执行的任务
	 */
	public abstract void runTask();
	private class MyRunable implements Runnable{

		@Override
		public void run() {
			if (flag) {
				runTask();
				mHandler.sendEmptyMessage(8);
			}
		}
	}
	/**
	 * 执行定时任务
	 * @param time 每隔多少秒执行
	 */
	public  void startTask(int time) {
		flag=true;
		ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleWithFixedDelay(new MyRunable(), 2, time, TimeUnit.SECONDS);
	}
	/*
	 * 暂停任务
	 */
	public void stop(){
		flag=false;
	}
	/**
	 * 重新开始任务
	 */
	public void start(){
		flag=true;
	}

}