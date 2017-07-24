package com.itheima.image;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
/*
网络图片的下载,因为ImageView设置图片需要一个Bitmap,所以要把图片流转成Bitmap对象
        ImageView imageIV = (ImageView) findViewById(R.id.imageIV);
        
// 开启一条新线程, 判断缓存文件夹大小, 如果超出一个指定的值, 删除一些比较老的图片
    }   
    public void go(View view) {
    	try {
			String address = addressET.getText().toString();
			ImageService service = new ImageService(this);
			Bitmap bm = service.getImage(address);//获取bitmap对象
			imageIV.setImageBitmap(bm);设制图片
*/
public class ImageService {
	private Context context;

	public ImageService(Context context) {
		this.context = context;
	}

	public Bitmap getImage(String address) throws Exception {
		URL url = new URL(address);		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();	
		conn.setConnectTimeout(3000);
		
		File cacheFile = new File(context.getCacheDir(), URLEncoder.encode(address));		
		if (cacheFile.exists()) {
			conn.setIfModifiedSince(cacheFile.lastModified());	// 获取文件的最后修改时间, 作为请求头
		}
		
		int code = conn.getResponseCode();
		System.out.println(code);
		if (code == 200) {
			byte[] data = read(conn.getInputStream());							// 读取服务端写回的数据
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);		// 把字节数据解码为图片
			bm.compress(CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));	// 存储图片到本地, 用作缓存. 建议新线程中处理
			return bm;	
		} else if (code == 304) {
			Bitmap bm = BitmapFactory.decodeFile(cacheFile.getAbsolutePath());		// 读取cacheFile, 生成Bitmap
			return bm;
		}
		
		throw new NetworkErrorException("连接出错: " + code);
	}
	//将输入流转成字节
	public  byte[] read(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1)
			baos.write(buffer, 0, len);
		in.close();
		baos.close();
		return baos.toByteArray();
	}
}
