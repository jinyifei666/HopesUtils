//自定义Toast

		view = View.inflate(this, R.layout.ui_toast, null);
		int which = sp.getInt("which", 0);
		view.setBackgroundResource(bgs[which]);
		//定义里面的文字
		TextView tv = (TextView) view.findViewById(R.id.tv_toast_address);
		tv.setText(AddressDao.getAddress(incomingNumber));
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.TOP | Gravity.LEFT;//定义参照点
		int lastx = sp.getInt("lastx", 0);
		int lasty = sp.getInt("lasty", 0);
		//定义具体位置
		params.x = lastx;
		params.y = lasty;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		//定义标记
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;// 不允许获取焦点持屏幕常亮
		params.format = PixelFormat.TRANSLUCENT;// 半透明效果
		// TYPE_PRIORITY_PHONE表示窗体类型为一种电话商口且可以触摸的类型,如果是TOAST类型是无法触摸的.要加上System.alert.windows权限就可以.		
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		params.setTitle("Toast");
		// 添加电话拨号屏幕上移动view的方法
		windowService.addView(view, params);

	}