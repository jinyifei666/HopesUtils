	1.在onDrawFrame方法中定义,记住time初始化为System.currentTimeMillis();
	fps++;
	if (System.currentTimeMillis() - time >= 1000) {
		System.out.println(fps);//这里每输出的就是帧率/秒
		fps = 0;
		time = System.currentTimeMillis();
	}