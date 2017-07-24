RunningAppProcessInfo
//获取所用系统运行的进程
public List<RunningAppProcessInfo> getRunningAppProcesses()
RunningAppProcessInfo对象可以获得进程名和进程pid 
	//因这有两个成员字段,如下:
	public String processName;
	public int pid;
MemoryInfo
//MemoryInfo可以获得进程占用的内存大小.
long memsize = activityManager.getProcessMemoryInfo(new int[]{processInfo.pid})[0].getTotalPrivateDirty()*1024;