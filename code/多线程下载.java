package com.itheima.downloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class BreakpointDownloader {
	private static final String DIR_PATH = "/mnt/sdcard/";	// 下载目录
	private static final int THREAD_AMOUNT = 3;				// 总线程数
	
	private URL url;			// 目标下载地址
	private File dataFile;		// 本地文件
	private File tempFile;		// 用来存储每个线程下载的进度的临时文件
	private long threadLen;		// 每个线程要下载的长度
	private long totalFinish;	// 总共完成了多少
	private long totalLen;		// 服务端文件总长度
	private long begin;			// 用来记录开始下载时的时间

	private Handler handler;
	private boolean isPause;

	public BreakpointDownloader(String address, Handler handler) throws Exception {
		url = new URL(address);															// 记住下载地址
		dataFile = new File(DIR_PATH, address.substring(address.lastIndexOf("/") + 1));	// 截取地址中的文件名, 创建本地文件
		tempFile = new File(dataFile.getAbsolutePath() + ".temp");						// 在本地文件所在文件夹中创建临时文件
		this.handler = handler;
	}
	
	public void pause() {
		isPause = true;
	}

	public void download() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		
		totalLen = conn.getContentLength();									// 获取服务端发送过来的文件长度
		threadLen = (totalLen + THREAD_AMOUNT - 1) / THREAD_AMOUNT;			// 计算每个线程要下载的长度
		
		// 发送文件大小
		Message msg = new Message();
		msg.getData().putLong("totalLen", totalLen);
		msg.what = 1;
		handler.sendMessage(msg);
		
		if (!dataFile.exists()) {											// 如果本地文件不存在
			RandomAccessFile raf = new RandomAccessFile(dataFile, "rws");	// 在本地创建文件
			raf.setLength(totalLen);										// 设置文件的大小和服务端相同
			raf.close();
		}
		
		if (!tempFile.exists()) {											// 如果临时文件不存在
			RandomAccessFile raf = new RandomAccessFile(tempFile, "rws");	// 创建临时文件, 用来记录每个线程已下载多少
			for (int i = 0; i < THREAD_AMOUNT; i++)							// 按照线程数循环
				raf.writeLong(0);											// 写入每个线程的开始位置(都是从0开始)
			raf.close();
		}
		
		for (int i = 0; i < THREAD_AMOUNT; i++)	// 按照线程数循环
			new DownloadThread(i).start();		// 开启线程, 每个线程将会下载一部分数据到本地文件中
		
		begin = System.currentTimeMillis();		// 记录开始时间
	}
	
	private class DownloadThread extends Thread {
		private int id; 	// 用来标记当前线程是下载任务中的第几个线程
		
		public DownloadThread(int id) {
			this.id = id;
		}
		
		public void run() {
			try {
				RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rws");		// 用来记录下载进度的临时文件
				tempRaf.seek(id * 8);						// 将指针移动到当前线程的位置(每个线程写1个long值, 占8字节)
				long threadFinish = tempRaf.readLong();		// 读取当前线程已完成了多少
				synchronized(BreakpointDownloader.this) {	// 多个下载线程之间同步
					totalFinish += threadFinish;			// 统计所有线程总共完成了多少
				}
				
				long start = id * threadLen + threadFinish;		// 计算当前线程的起始位置
				long end = id * threadLen + threadLen - 1;		// 计算当前线程的结束位置
				System.out.println("线程" + id + ": " + start + "-" + end);
			
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);		// 设置当前线程下载的范围
				
				InputStream in = conn.getInputStream();								// 获取连接的输入流
				RandomAccessFile dataRaf = new RandomAccessFile(dataFile, "rws");	// 装载数据的本地文件(可以理解为输出流)
				dataRaf.seek(start);												// 设置当前线程保存数据的位置
				
				byte[] buffer = new byte[1024 * 100];			// 每次拷贝100KB
				int len;
				while ((len = in.read(buffer)) != -1) {
					if (isPause)
						return;
					dataRaf.write(buffer, 0, len);				// 从服务端读取数据, 写到本地文件
					threadFinish += len;						// 每次写入数据之后, 统计当前线程完成了多少
					tempRaf.seek(id * 8);						// 将临时文件的指针指向当前线程的位置
					tempRaf.writeLong(threadFinish);			// 将当前线程完成了多少写入到临时文件
					synchronized(BreakpointDownloader.this) {	// 多个下载线程之间同步
						totalFinish += len;						// 统计所有线程总共完成了多少
						// 发送当前进度
						Message msg = new Message();
						msg.getData().putLong("totalFinish", totalFinish);
						msg.what = 2;
						handler.sendMessage(msg);
					}
				}
				dataRaf.close();
				tempRaf.close();
				
				System.out.println("线程" + id + "下载完毕");
				if (totalFinish == totalLen) {					// 如果已完成长度等于服务端文件长度(代表下载完成)
					System.out.println("下载完成, 耗时: " + (System.currentTimeMillis() - begin));
					tempFile.delete();							// 删除临时文件
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

