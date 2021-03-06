package cn.itheima.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import cn.itheima.mobilesafe.utils.Logger;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;
import cn.itheima.mobilesafe.domain.UpdateInfo;
import cn.itheima.mobilesafe.engine.UpdateInfoParser;
import cn.itheima.mobilesafe.utils.CopyFileUtils;
import cn.itheima.mobilesafe.utils.DownLoadUtil;

public class SplashActivity extends Activity {
	public static final int PARSE_XML_ERROR = 10;
	public static final int PARSE_XML_SUCCESS = 11;
	public static final int SERVER_ERROR = 12;
	public static final int URL_ERROR = 13;
	public static final int NETWORK_ERROR = 14;
	private static final int DOWNLOAD_SUCCESS = 15;
	private static final int DOWNLOAD_ERROR = 16;
	protected static final String TAG = "SplashActivity";
	protected static final int COPY_ERROR = 17;
	private TextView tv_splash_version;
	private UpdateInfo updateInfo;

	private ProgressDialog pd;// 下载进度的对话框

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PARSE_XML_ERROR:
				Toast.makeText(getApplicationContext(), "解析xml失败", 0).show();
				// 进入程序主界面
				loadMainUI();
				break;
			case SERVER_ERROR:
				Toast.makeText(getApplicationContext(), "服务器异常", 0).show();
				// 进入程序主界面
				loadMainUI();
				break;
			case URL_ERROR:
				Toast.makeText(getApplicationContext(), "服务器地址异常", 0).show();
				// 进入程序主界面
				loadMainUI();
				break;
			case NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常", 0).show();
				// 进入程序主界面
				loadMainUI();
				break;
			case PARSE_XML_SUCCESS:
				if (getAppVersion().equals(updateInfo.getVersion())) {
					// 进入程序主界面
					Logger.i(TAG, "版本号相同,进入主界面");
					loadMainUI();
				} else {
					Logger.i(TAG, "版本号不相同,弹出来升级提示对话框");
					showUpdateDialog();
				}
				break;
			case DOWNLOAD_ERROR:
				Toast.makeText(getApplicationContext(), "下载失败", 0).show();
				// 进入程序主界面
				loadMainUI();
				break;
			case DOWNLOAD_SUCCESS:
				File file = (File) msg.obj;
				Logger.i(TAG, "安装apk" + file.getAbsolutePath());
				// 安装apk
				installApk(file);
				finish();
				break;
			case COPY_ERROR:
				Toast.makeText(getApplicationContext(), "拷贝数据库异常", 0).show();
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Intent intent = new Intent();
//		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//		Intent shortCutIntent = new Intent();
//		shortCutIntent.setAction("cn.itheima.xxx");
//		shortCutIntent.addCategory(Intent.CATEGORY_DEFAULT);
//		
//		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
//    	intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "黑马卫士");
//    	intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.atools));
//    	sendBroadcast(intent);
		
		setContentView(R.layout.activity_splash);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本:" + getAppVersion());

		// 连接服务器 检查版本更新.
		new Thread(new CheckVersionTask()).start();

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(2000);
		findViewById(R.id.rl_splash).startAnimation(aa);

		// 拷贝关键文件到系统目录.
		copyAddressDB();

		copyCommonNumDB();
		
		copyVirusDB();
	}

	/**
	 * 释放 数据库文件到系统目录
	 */
	private void copyAddressDB() {
		final File file = new File(getFilesDir(), "address.db");
		if (file.exists() && file.length() > 0) {
			//do nothing
		} else {
			new Thread() {
				public void run() {
					Message msg = Message.obtain();
					try {
						InputStream is = getAssets().open("address.db");
						File f = CopyFileUtils.copyFile(is, file.getAbsolutePath());
						if(f!=null){
							// 拷贝成功.
						}else{
							msg.what = COPY_ERROR;
						}
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = COPY_ERROR;
					}finally{
						handler.sendMessage(msg);
					}
				};
			}.start();
		}

	}
	/**
	 * 释放 常用号码数据库到系统目录
	 */
	private void copyCommonNumDB() {
		final File file = new File(getFilesDir(), "commonnum.db");
		if (file.exists() && file.length() > 0) {
			//do nothing
		} else {
			new Thread() {
				public void run() {
					Message msg = Message.obtain();
					try {
						InputStream is = getAssets().open("commonnum.db");
						File f = CopyFileUtils.copyFile(is, file.getAbsolutePath());
						if(f!=null){
							// 拷贝成功.
						}else{
							msg.what = COPY_ERROR;
						}
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = COPY_ERROR;
					}finally{
						handler.sendMessage(msg);
					}
				};
			}.start();
		}

	}
	/**
	 * 释放病毒数据库到系统目录
	 */
	private void copyVirusDB() {
		final File file = new File(getFilesDir(), "antivirus.db");
		if (file.exists() && file.length() > 0) {
			//do nothing
		} else {
			new Thread() {
				public void run() {
					Message msg = Message.obtain();
					try {
						InputStream is = getAssets().open("antivirus.db");
						File f = CopyFileUtils.copyFile(is, file.getAbsolutePath());
						if(f!=null){
							// 拷贝成功.
						}else{
							msg.what = COPY_ERROR;
						}
					} catch (Exception e) {
						e.printStackTrace();
						msg.what = COPY_ERROR;
					}finally{
						handler.sendMessage(msg);
					}
				};
			}.start();
		}

	}
	/**
	 * 安装一个apk文件
	 * 
	 * @param file
	 */
	protected void installApk(File file) {
		// <action android:name="android.intent.action.VIEW" />
		// <category android:name="android.intent.category.DEFAULT" />
		// <data android:scheme="content" />
		// <data android:scheme="file" />
		// <data android:mimeType="application/vnd.android.package-archive" />
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		// intent.setType("application/vnd.android.package-archive");
		// intent.setData(Uri.fromFile(file));
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	/**
	 * 自动升级的提示对话框
	 */
	protected void showUpdateDialog() {

		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("升级提醒");
		builder.setMessage(updateInfo.getDescription());
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String apkurl = updateInfo.getApkurl();
				pd = new ProgressDialog(SplashActivity.this);
				pd.setTitle("升级操作");
				pd.setMessage("正在下载...");
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.show();
				Logger.i(TAG, "下载后安装:" + apkurl);
				final File file = new File(Environment
						.getExternalStorageDirectory(), DownLoadUtil
						.getFileName(apkurl));
				// 判断sd卡是否可用,只有可用状态.
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					new Thread() {
						public void run() {
							File savedFile = DownLoadUtil.download(
									updateInfo.getApkurl(),
									file.getAbsolutePath(), pd);
							Message msg = Message.obtain();
							if (savedFile != null) {
								// 下载成功
								msg.what = DOWNLOAD_SUCCESS;
								msg.obj = savedFile;
							} else {
								// 下载失败
								msg.what = DOWNLOAD_ERROR;

							}
							handler.sendMessage(msg);
							pd.dismiss();
						};
					}.start();
				} else {
					Toast.makeText(getApplicationContext(), "sd卡不可用", 0).show();
					loadMainUI();
				}

			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				loadMainUI();
			}
		});
		builder.create().show();
	    //builder.show();
	}

	/**
	 * 进入主界面
	 */
	private void loadMainUI() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private class CheckVersionTask implements Runnable {
		@Override
		public void run() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean isupdate = sp.getBoolean("update", true);
			if (!isupdate) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loadMainUI();
				return;
			}
			long startTime = System.currentTimeMillis();
			Message msg = Message.obtain();
			try {
				URL url = new URL(getResources().getString(R.string.serverurl));
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(1500);
				int code = conn.getResponseCode();
				if (code == 200) {
					InputStream is = conn.getInputStream();
					updateInfo = UpdateInfoParser.getUpdateInfo(is);
					if (updateInfo == null) {
						// TODO:解析xml失败
						msg.what = PARSE_XML_ERROR;
					} else {
						// 解析成功
						msg.what = PARSE_XML_SUCCESS;
					}
				} else {
					// TODO:服务器内部错误.
					msg.what = SERVER_ERROR;
				}
			} catch (MalformedURLException e) {
				msg.what = URL_ERROR; // http://
				e.printStackTrace();
			} catch (NotFoundException e) {
				msg.what = URL_ERROR; //
				e.printStackTrace();
			} catch (IOException e) {
				msg.what = NETWORK_ERROR;
				e.printStackTrace();
			} finally {
				long endTime = System.currentTimeMillis();
				long dTime = endTime - startTime;
				if (dTime < 2000) {
					try {
						Thread.sleep(2000 - dTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				handler.sendMessage(msg);
			}

		}
	}

	/**
	 * 获取应用程序的版本号
	 * 
	 * @return
	 */
	private String getAppVersion() {
		// 得到系统的包管理器
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			return packinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// can't reach
			return "";
		}

	}

}