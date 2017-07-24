/**
	 * 下载文件操作
	 * 
	 * @param serverPath
	 *            服务器文件的路径
	 * @param savedPath
	 *            本地保存的路径
	 * @param pd 进度条对话框
	 * @return 下载成功 返回文件对象 下载失败 返回null
	 */
	 //首先在Activity获取这些参数,再传进来
public void onClick(DialogInterface dialog, int which) {
//创建一个进度条对象
	ProgressDialog pd = new ProgressDialog(SplashActivity.this);
		pd.setTitle("升级操作");
		pd.setMessage("正在下载...");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设制进度条样式:这里表示长条形
		pd.show();//记得要show!
		//构造一个文件,通过sd卡路径和文件名 
		String apkurl = updateInfo.getApkurl();
		final File file = new File(Environment.getExternalStorageDirectory(),apkurl.substring(apkurl.lastIndexOf("/")+1);
		// 判断sd卡是否可用,因为要下载到sd卡,记得给访问网络和sd写入权限.
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//网络访问都用新线程	
			new Thread() {
				public void run() {
					File savedFile = DownLoadUtil.download(updateInfo.getApkurl(),file.getAbsolutePath(),pd);
					Message msg = Message.obtain();
					if(savedFile!=null){
						//下载成功,用message传递给handler去做安装处理
						msg.what = DOWNLOAD_SUCCESS;
						msg.obj = savedFile;
					}else{
						//下载失败,返回一个错误码给handler
						msg.what = DOWNLOAD_ERROR;						
					}
					handler.sendMessage(msg);
					pd.dismiss();//一定要记得提交!
				};
			}.start();
		}else{
			Toast.makeText(getApplicationContext(), "sd卡不可用", 0).show();
			loadMainUI();
		}
}
//下载的方法
public static File download(String serverPath, String savedPath, ProgressDialog pd) {
		try {
			URL url = new URL(serverPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (code == 200) {
				pd.setMax(conn.getContentLength());//设定进度条的最大值
				InputStream is = conn.getInputStream();
				File file = new File(savedPath);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				int total = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total +=len;
					pd.setProgress(total);//设制进度条临时数据,让其动态变化
					Thread.sleep(20);
				}
				fos.flush();//记得回刷
				fos.close();
				is.close();
				return file;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}