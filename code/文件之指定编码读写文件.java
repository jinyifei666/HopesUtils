try {
	File f = new File("E:\\eclipse\\workspace\\box\\Data\\2003-3-7-100.inc");
	InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
	BufferedReader reader = new BufferedReader(read);
	String line;
	while ((line = reader.readLine()) != null) {
		System.out.println(line);
	}
	File f2 = new File("E:\\eclipse\\workspace\\box\\Data\\2003-3-7-100.inc");
	InputStreamReader read1 = new InputStreamReader(new FileInputStream(f), "UTF-8");
	BufferedReader reader2 = new BufferedReader(read);
	String line2;
	while ((line = reader.readLine()) != null) {
		System.out.println(line);
	}
	File file = new File("c:/a.test");
	Writer writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
	writer.write("中文测试");
	writer.close();
} catch (Exception e) {
	e.printStackTrace();
}