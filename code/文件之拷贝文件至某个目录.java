	/**
	 * 拷贝文件到系统的某个目录
	 * 
	 * @param is
	 *            源文件的流
	 * @param destPath
	 *            目标路径
	 * @return
	 */
	public static File copyFile(InputStream is, String destPath) {
		try {
			File file = new File(destPath);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}