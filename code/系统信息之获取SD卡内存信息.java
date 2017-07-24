1.获取sdcard卡的内存空间

// Sdcard的可用空间  
public String getAvailSD() {
		//判断是否有插入存储卡
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File path = Environment.getExternalStorageDirectory();//获取sd路径
			// StatFs:检索有关整体上的一个文件系统的空间信息 
			StatFs stat = new StatFs(path.getPath());
			 // 一个文件系统的块大小，以字节为单位。  
			long blockSize = stat.getBlockSize();
			 // SD卡中可用的块数  
			long availableBlocks = stat.getAvailableBlocks();
			return Formatter.formatFileSize(this, blockSize * availableBlocks);
		}else{
			return null;
		}
	}

    // Sdcard的总空间  
    public String getALLAvailSdSize() {  
		//判断是否有插入存储卡
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File path = Environment.getExternalStorageDirectory();  
			// StatFs:检索有关整体上的一个文件系统的空间信息  
			StatFs stat = new StatFs(path.getPath());  
			// 一个文件系统的块大小，以字节为单位。  
			long blockSize = stat.getBlockCount();  
			// SD卡中可用的块数  
			long availableBlocks = stat.getAvailableBlocks();  
			return Formatter.formatFileSize(this, blockSize * availableBlocks);  
		}else{
			return null;
		}
    }  