	// 在OpenGL里面绘制文字
		 Bitmap b = Bitmap.createBitmap(156, 80, Bitmap.Config.ARGB_4444);
		//根据图片定义画布
		 Canvas c = new Canvas(b);
		 //定义画笔
		 Paint p = new Paint();
		 //给画笔定义颜色
		 p.setColor(Color.RED);
		 //定义字体大小
		 p.setTextSize(30);
		 //写字,第二和三个参数是表示坐标.
		 c.drawText("Hello", 0, 30, p);
		 //这样图片上就有字了
		 return  b;//用这个bitmap去生成纹理就可以了.