通过包名获取应用程序信息对象,并获取应用程序的名称和图标
//content.ContextWrapper.getPackageManager()
PackageManager pm = getPackageManager();
ApplicationInfo info = pm.getApplicationInfo(packname, 0);
Drawable icon = info.loadIcon(pm);
CharSequence name = info.loadLabel(pm);
//获得后就可设置到想设置的地方
TextView tv = (TextView) findViewById(R.id.tv_name);
tv.setText(name);
ImageView iv = (ImageView) findViewById(R.id.iv_icon);
iv.setImageDrawable(icon);