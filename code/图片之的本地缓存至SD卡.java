图片的本地缓存,为了节省资源
/**
	 * 将一个Bitmap转成sd卡保存在本地SD卡
	 * @param bm Bitmap
	 * @param url 图片的网络url
	 */
	private void saveBmpToSd(Bitmap bm, String url) {
		if (bm == null) {
			Log.w("saveBmpToSd", " trying to savenull bitmap");
			return;
		}
		File sdFile = Environment.getExternalStorageDirectory();
		long freeSpace = sdFile.getFreeSpace();
		String s = Formatter.formatFileSize(context, freeSpace);
		long FREE_SD_SPACE_NEEDED_TO_CACHE = 10 * 1024L;
		// 判断sdcard上的空间是否小于10MB.
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpace) {
			Log.w("saveBmpToSd", "Low free space onsd, do not cache");
			return;
		}
		// 根据url获取文件名
		String filename = url.substring(url.lastIndexOf("/") + 1);
		//根据url获取目录名
		String replaceUrl = url.replace("http://", "");
		String dirName = replaceUrl.substring(0,replaceUrl.lastIndexOf("/") + 1);
		//创建文件
		File file = new File(dirName, filename);
		//定义时间戳
		long newModifiedTime =System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
		//写入SD卡
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			//将bitmap转换成.jpg格式保存在sd卡
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
			Log.i("TAG", "Image saved tosd");
		} catch (FileNotFoundException e) {
			Log.w("TAG", "FileNotFoundException");
		} catch (IOException e) {
			Log.w("TAG", "IOException");
		}
	}