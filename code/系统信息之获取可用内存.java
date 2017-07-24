//获取手机自带可用内存rom.
public String getAvailRom() {
		File path = Environment.getDataDirectory();
		//StatFs用于获取一个
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(this, blockSize * availableBlocks);
	}


		