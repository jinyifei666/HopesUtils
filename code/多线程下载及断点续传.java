package com.itheima.downloader;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiThreadDownloader {
	
	private URL url;		// 目标地址
	private File file;		// 本地路径
	private long threadLen;	// 每个线程下载多少
	
	private static final int THREAD_AMOUNT = 3;				// 线程数
	private static final String DIR_PATH = "/mnt/sdcard/";	// 下载目录

	public MultiThreadDownloader(String address) throws IOException {	
		url = new URL(address);															// 记住服务端文件下载地址
		file = new File(DIR_PATH, address.substring(address.lastIndexOf("/") + 1));		// 截取地址中的文件名, 创建本地文件路径
	}

	public void download() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		
		long totalLen = conn.getContentLength();					// 获取文件长度
		threadLen = (totalLen + THREAD_AMOUNT - 1) / THREAD_AMOUNT;	// 计算每个线程要下载的长度. (100 + 3 - 1) / 3 = 34
		
		RandomAccessFile raf = new RandomAccessFile(file, "rws");	// 在本地创建一个和服务端大小相同的文件
		raf.setLength(totalLen);									// 设置文件的大小
		raf.close();
		
		for (int i = 0; i < THREAD_AMOUNT; i++)		// 按照线程数循环
			new DownloadThread(i).start();			// 开启线程, 每个线程将会下载一部分数据到本地文件中
	}
	
	private class DownloadThread extends Thread {
		private int id; 	// 用来标记当前线程是下载任务中的第几个线程
		
		public DownloadThread(int id) {
			this.id = id;
		}
		
		public void run() {
			long start = id * threadLen;					// 起始位置
			long end = id * threadLen + threadLen - 1;		// 结束位置. 
			System.out.println("线程" + id + ": " + start + "-" + end);
			
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);	// 设置当前线程下载的范围
				
				InputStream in = conn.getInputStream();							// 获取连接的输入流, 用来读取服务端数据
				RandomAccessFile raf = new RandomAccessFile(file, "rws");		// 随机读写文件, 用来向本地文件写出
				raf.seek(start);												// 设置保存数据的位置
				
				byte[] buffer = new byte[1024 * 100];	// 每次拷贝100KB
				int len;
				while ((len = in.read(buffer)) != -1)
					raf.write(buffer, 0, len);			// 从服务端读取数据, 写到本地文件
				raf.close();
				
				System.out.println("线程" + id + "下载完毕");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

