//在android系统下使用外部资源的几种方式:

//1. res - raw 目录下
InputStream is = getResources().openRawResource(R.raw.address);


//2. assets 资产目录.
InputStream is = getAssets().open("address.db");
在2.3 以后才完全支持.
模拟器 2.2 之前不允许大于1M的资源.


//3. 放入src目录下,类加载器方式.jar包基本用这个.
InputStream is  =  getClassLoader().getResourceAsStream("address.db");


//4. 特殊方法.如联网下载数据文件到sd卡.

//获得输入流后,写入就行.
public class CopyUtils {
//需要一个文件目录absolutePath,用一个文件getAbsolutePath就能获得
	File file=new File(getFilesDir(),"address.db");
	String absolutePath =file.getAbsolutePath();
	public static File copyAddress(InputStream addressIn, String absolutePath) {
		OutputStream out;
		File file = new File(absolutePath);
		try {
			out = new FileOutputStream(file);
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len=addressIn.read(buffer))!= -1) {
				out.write(buffer, 0, len);
				out.flush();
			}
			out.close();
			addressIn.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return file;

	}
}