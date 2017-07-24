1.应用之获得占用内存大小
public static String getTaskInfos(String packName){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		for(RunningAppProcessInfo processInfo: processInfos){
			if(packName.equals(processInfo.processName)){
				long memsize = am.getProcessMemoryInfo(new int[]{processInfo.pid})[0].getTotalPrivateDirty()*1024;
				return Formatter.formatFileSize(getApplicationContext(),memsize);				
			}
		}
		return null;
	}