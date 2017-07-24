ActivityManager的用法
ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//注:maxNum表示集合的最多个数
//获取最近的应用，最后启动的排前
//第二个参数是ActivityManager自已定义的一些标记,如:ActivityManager.RECENT_IGNORE_UNAVAILABLE,这个标记表示忽略一些难以获得的
 public List<RecentTaskInfo> getRecentTasks(int maxNum, int flags)
 
//获取当前运行的任务栈的信息 (一个Activity要显示必须压入栈中,栈顶显示的就是当你可见的Activity)
public List<RunningTaskInfo> getRunningTasks(int maxNum)//要GET_TASKS权限

//获取当前运行的service应用
 public List<RunningServiceInfo> getRunningServices(int maxNum)
 
//获取所用系统运行的进程
public List<RunningAppProcessInfo> getRunningAppProcesses()
RunningAppProcessInfo对象可以获得进程名和进程pid 
	//因这有两个成员字段,如下:
	public String processName;
	public int pid;

//通过int[] pids数组的MemoryInfo[] 数组
MemoryInfo[] getProcessMemoryInfo(int[] pids)
	//MemoryInfo可以获得进程占用的内存大小.
	long memsize = activityManager.getProcessMemoryInfo(new int[]{processInfo.pid})[0].getTotalPrivateDirty()*1024;

//获取所用系统运行的有问题进程,如果为空表示系统所有进程正常
public List<ProcessErrorStateInfo> getProcessesInErrorState()



//判断用户是不是一个猴子,也就是如果返回真,那么所有的设置都会无效,比如清除用户所有测试.
ActivityManager.isUserAMonkey

//获得手机还有多少剩余可用内存,这个方法是把信息存入参数中的MemoryInfo对象,然后MemoryInfo对象的成员availMem就是剩余内存long值.
public void getMemoryInfo(MemoryInfo outInfo

//杀死指定进程
public void killBackgroundProcesses(String packageName)
	
常见应用:
//杀死后台所有的进程
ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	List<RunningAppProcessInfo>  infos = am.getRunningAppProcesses();
	for(RunningAppProcessInfo info : infos){
		am.killBackgroundProcesses(info.processName);
	}
//看手机还有多少可用内存
public static long getAvailRam(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
//获取手机正在运行的进程的个数
public static int getRunningProcessCount(Context context) {
		// 得到系统的任务管理器.
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningAppProcesses().size();
	}