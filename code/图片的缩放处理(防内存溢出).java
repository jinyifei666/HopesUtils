
//设置图片缩放的几种方法:原理都是通过获得图片信息,再给定Options缩放值,生成图片

//方式一:自定义缩放比例:全部统一
/**
	 * 缩放图片
	 * @param imagePath
	 * @return 一个缩放好的bitmap
	 */
	private Bitmap getZoomBitmap(String imagePath) {
		// 解决图片内存溢出问题
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//这样就只返回图片参数
		// 获取这个图片的宽和高
		Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;//上面操作完后,要设回来,不然下面同样获取不到实际图片
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		//上面算完后一下如果比200大,那就be就大于1,那么就压缩be,如果比200小,那图片肯定很小了,直接按原图比例显示就行
		if (be <= 0){be = 1;}
		options.inSampleSize = be;//be=2.表示压缩为原来的1/2,以此类推
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false,不然返回的还是一个空bitmap
		bm = BitmapFactory.decodeFile(imagePath, options);
		return bm;
	}

	
//	方式二:通过图片大小计算options.inSampleSize的值
public static Bitmap fitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	} 
	
	//Android官方提供的方式:也是计算inSampleSize的值,直接返回
	
		// 动态获取缩放比例
	public static int computeSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
	double w = options.outWidth;
	double h = options.outHeight;
	int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),Math.floor(h / minSideLength));
	if (upperBound < lowerBound) {
		return lowerBound;
	}
	if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
		return 1;
	} else if (minSideLength == -1) {
		return lowerBound;
	} else {
		return upperBound;
	}
}
