// 收集读取用户的日志.
		new Thread() {
			public void run() {
				try {
					File file = new File(Environment.getExternalStorageDirectory(),"log.txt");
					FileOutputStream fos = new FileOutputStream(file);
					Process process = Runtime.getRuntime().exec("logcat");
					InputStream is = process.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String line;
					while ((line = br.readLine()) != null) {
						if(line.contains("I/ActivityManager")){
							fos.write(line.getBytes());
							fos.flush();
						}
					}
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();