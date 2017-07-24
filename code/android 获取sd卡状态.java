/** 获取存储卡路径 */ 
File sdcardDir=Environment.getExternalStorageDirectory(); 
/** StatFs 看文件系统空间使用情况 */ 
StatFs statFs=new StatFs(sdcardDir.getPath()); 
/** Block 的 size*/ 
Long blockSize=statFs.getBlockSize(); 
/** 总 Block 数量 */ 
Long totalBlocks=statFs.getBlockCount(); 
/** 已使用的 Block 数量 */ 
Long availableBlocks=statFs.getAvailableBlocks(); 
