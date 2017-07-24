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
	public static File download(String serverPath, String savedPath, ProgressDialog pd) {
		try {
			URL url = new URL(serverPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if (code == 200) {
				pd.setMax(conn.getContentLength());
				InputStream is = conn.getInputStream();
				File file = new File(savedPath);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				int total = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total +=len;
					pd.setProgress(total);
					Thread.sleep(20);
				}
				fos.flush();
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
	/**
	 * 获取服务器文件的名称
	 * @param serverPath
	 * @return
	 */
	public static String getFileName(String serverPath){
		return serverPath.substring(serverPath.lastIndexOf("/")+1);
	}
}