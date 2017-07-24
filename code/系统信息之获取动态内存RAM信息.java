1.手机动态内存RAM的获取
//获取手机总动态内存ram大小
	public  String getTotalRam() {
			try {
				File file = new File("/proc/meminfo");
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				// MemTotal: 253604 kB
				String result = br.readLine();
				//提取一行里面的数字
				StringBuffer sb = new StringBuffer();
				char[] chars = result.toCharArray();
				for (char c : chars) {
					if (c >= '0' && c <= '9') {
						sb.append(c);
					}
				}
		//单位是kB,所以要转换成byte,方便用Formatter.formatFileSize方法格式化成字符串.
				long number= Long.parseLong(sb.toString()) * 1024;
				return Formatter.formatFileSize(this, number);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

//获取手机剩余动态内存ram大小	
1.方式一:
	public String getAvailRam(Context context) {
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo outInfo = new MemoryInfo();
			am.getMemoryInfo(outInfo);
			 long number=outInfo.availMem;
			return Formatter.formatFileSize(this, number);
		}
2.方式二:
	public String getInternalAvailSize() {
		StatFs mDataFileStats = new StatFs("/data");
		long freeStorage = (long) mDataFileStats.getAvailableBlocks()
				* mDataFileStats.getBlockSize();
		return Formatter.formatFileSize(this, freeStorage);
}