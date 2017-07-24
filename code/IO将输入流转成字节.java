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