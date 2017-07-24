//定时任务:可以在特定的时间,特定的日期,以什么样的周期执行.
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		
		@Override
		public void run() {

			
		}
	};
	//第一次执行延期1秒,以后每隔两秒执行一次.
	timer.schedule(task, 1000, 2000);
	
	//第一次在什么日期执行,以后每隔多久执行一次.
	public void schedule(TimerTask task, Date when, long period)
	
	
	//这个方法是表示任务在什么日期执行一次
	 public void schedule(TimerTask task, Date when) 
	 //这个方法是表示任务在调用之个方法后多久执行一次
	 public void schedule(TimerTask task, long delay)
	 
	 /*schedule和scheduleAtFixedRate的区别在于，如果指定开始执行的时间在当前系统运行时间之前，
	 scheduleAtFixedRate会把已经过去的时间也作为周期执行，而schedule不会把过去的时间算上。*/
	 
	 //这个方法是表示任务在什么日期执行一次
	 public void scheduleAtFixedRate(TimerTask task, Date when) 
	 //这个方法是表示任务在调用之个方法后多久执行一次
	 public void scheduleAtFixedRate(TimerTask task, long delay)
	 
	// 取消线定时任务
	timer.cancel();
	task = null;
	timer = null;