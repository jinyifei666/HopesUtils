package com.itheima.phonelistener;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneService extends Service {

	private MediaRecorder mRecorder;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		System.out.println("监听电话状态!");
		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);	// 获取电话管理器
		manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);	// 用电话管理器注册一个监听器, 监听电话状态
	}
	
	private class MyPhoneStateListener extends PhoneStateListener {
		private String num;
		
		public void onCallStateChanged(int state, String incomingNumber) {		// 电话状态改变时执行该方法
			switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					num = incomingNumber;	// 振铃时, 记录号码
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					startRecording();		// 摘机时, 开始录音
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					stopRecording();		// 空闲时, 结束录音
					break;
			}
		}
		
		private void stopRecording() {
			if (mRecorder != null) {
				mRecorder.stop();		// 停止
		        mRecorder.release();	// 释放资源
		        mRecorder = null;		// 垃圾回收
			}
		}

		private void startRecording() {
			try {
				mRecorder = new MediaRecorder();												// 创建媒体记录器
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);						// 设置音频源
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);				// 设置输出格式
				mRecorder.setOutputFile("/mnt/sdcard/" + num + "_" + System.currentTimeMillis() + ".3gp");	// 设置输出文件路径
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);					// 设置编码
				mRecorder.prepare();	// 准备
				mRecorder.start();		// 开始
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
