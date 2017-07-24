package com.itheima.audiocapture;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
/*记得在manifest中注册: <application       
        <service android:name=".AudioCaptureService"/>
    </application>
*/
public class AudioCaptureService extends Service {
	private MediaRecorder mRecorder;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		startRecording();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopRecording();
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
			mRecorder.setOutputFile("/mnt/sdcard/" + System.currentTimeMillis() + ".3gp");	// 设置输出文件路径
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);					// 设置编码
			mRecorder.prepare();	// 准备
			mRecorder.start();		// 开始
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
