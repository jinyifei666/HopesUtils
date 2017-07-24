//包管理器PackageManager的用法
//int flag标识的用法.这个标识用于标识要返回这个应用的什么信息.
1.GET_ACTIVITIES:	PackageInfo标志：返回有关活动，在活动的包。
2.GET_CONFIGURATIONS:	有关硬件的喜好在PackageInfo.configPreferences和要求的功能PackageInfo.reqFeatures PackageInfo标志：返回的信息 。
3.GET_DISABLED_COMPONENTS:	PackageInfo标志：在返回的信息中包括禁用的组件。
4.GET_GIDS:	PackageInfo标志：返回与应用程序关联的 组ID。
5.GET_INSTRUMENTATION:	PackageInfo标志：返回的信息包在仪器仪表中的 。
6.GET_INTENT_FILTERS	PackageInfo标志：活动支持的意图过滤器的返回信息。
7.GET_META_DATA	ComponentInfo标志：返回元数据的 数据包 s表示与组件关联的。
8.GET_PERMISSIONS	PackageInfo标志：返回的信息在包中的权限的权限 。
9.GET_PROVIDERS	PackageInfo标志：返回包中提供信息的内容提供商 。
10.GET_RECEIVERS	PackageInfo标志：包中的意图接收器在接收器的返回信息 。
11.GET_RESOLVED_FILTER	ResolveInfo标志：退回的IntentFilter的，相匹配的过滤器在一个特定的ResolveInfo 。
12.GET_SERVICES	PackageInfo标志：返回的信息服务，在服务包中的。
13.GET_SHARED_LIBRARY_FILES	ApplicationInfo标志：返回 路径的共享库 与应用程序相关联的。
14.GET_SIGNATURES	PackageInfo标志：返回的信息包中包含的签名。
15.GET_UNINSTALLED_PACKAGES	标志参数有关的所有应用程序（甚至卸载的），其中有数据目录检索一些信息。
16.GET_URI_PERMISSION_PATTERNS	ProviderInfo标志：返回的 URI ，都与内容提供商的许可模式。




2.常见应用:

1.获得所有程序签名:
public String[] getSignstr(){
	//获得包对象
	PackageManager pm = Context.getPackageManager();
	//获得所有包对象包括没有卸载干净的.GET_SIGNATURES表示把包签名信息也获得.GET_UNINSTALLED_PACKAGES,表示获得所有已安装的应用.
	List<PackageInfo> packinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES| PackageManager.GET_SIGNATURES);
	String[] sign=new String[packinfos.size()];
	int i=0;
	for (PackageInfo packinfo : packinfos) {
		//获得包的签名信息集合,虽然只有一个签名,但只是
		Signature[] signatures = packinfo.signatures;
		//获得签名
		String signstr = signatures[0].toCharsString();
		sign[i]=signstr;
		i++;
	}
	return sign;
}
2.获取程序的权限.
public  String[] getAppInfos(Context context) {
	PackageManager pm = context.getPackageManager();
	//PackageManager.GET_PERMISSIONS表示要获得应用的权限信息
	List<PackageInfo> packinfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
	for(PackageInfo packinfo : packinfos){
	//获得一个应用的所有权限数组		
		String[] permissions = packinfo.requestedPermissions;
		//获得后就可以遍历去判断应用有什么权限,并作相应的处理,比如这里判断这个应用是否在读取用户的隐私
		if(permissions!=null&&permissions.length>0){
			for(String permission : permissions){
				if("android.permission.INTERNET".equals(permission)){
					//这个应用有访问网络权限
				}else if("android.permission.READ_CONTACTS".equals(permission)){
					//这个应用有访问了联系人权限
				}else if("android.permission.ACCESS_FINE_LOCATION".equals(permission)){
					//这个应用有访问用户位置权限
				}else if("android.permission.SEND_SMS".equals(permission)){
					//这个应用有访问用户短信权限
				}
				
			}
		}	
	}
	return permissions;
}

3.判断一个应用的类型.
 
	 //是否是系统软件 true为系统软件
    public boolean isSystemApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);  
    }  
	//是否是系统软件的更新软件  true为系统软件的更新软件
    public boolean isSystemUpdateApp(PackageInfo pInfo) {  
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);  
    }  
	//是否是用户软件 true为用户软件
    public boolean isUserApp(PackageInfo pInfo) {  
	//如果即不是系统软件也不是系统软件的更新软件,那就就是用户
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));  
    }  
	//判断一个应用是否在手机内存 true安装在手机内存 false为安装在SD卡
	public boolean isInRom(PackageInfo pInfo) {  
		if((packinfo.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0){
			return true;//在手机内存
		}else{
			return false;//在SD卡
		}  
	}
	
4.获得应用的包名和图标和应用名
方式一:
public void getAppInfo(PackageInfo packinfo) {
	String version = packinfo.versionName;//获取应用版本
	String packname = packinfo.packageName;//获取应用包名
	String appname = packinfo.applicationInfo.loadLabel(pm).toString();//获取应用名 pm表示PackageManger
	Drawable appicon = packinfo.applicationInfo.loadIcon(pm);//获取应用图标.
	//获取这些信息可以封装成一个对象,或作其他处理.	
}
方式二:
public void getAppInfo(String packName) {
	ApplicationInfo  applicationInfo =	pm.getApplicationInfo(packName, 0);
	rawable appicon = applicationInfo.loadIcon(pm);
	String appname = applicationInfo.loadLabel(pm).toString();
}

5.通过包名开启一个应用程序
	private void startApk(String packname) {
		// 开启这个应用程序里面的第1个activity就把应用打开了.
		try {
			PackageInfo packinfo = getPackageManager().getPackageInfo(packname,PackageManager.GET_ACTIVITIES);//获得应用包对象
			ActivityInfo[] activityinfos = packinfo.activities;//获得所有的Activity对象
			if(activityinfos!=null&&activityinfos.length>0){
				ActivityInfo activityinfo = activityinfos[0];//获得第一个Activity对象
				String className = activityinfo.name;//获得Activity类名
				Intent intent = new Intent();
				intent.setClassName(selectedAppInfo.getPackname(), className);//通过应用的包名加Activity的类名定义的意图开启应用.
				startActivity(intent);
			}else{
				Toast.makeText(this, "无法启动应用程序!", 0).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "无法启动应用程序", 0).show();
		}
	}
5.分享应用.

	private void shareApk() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain
		//这个信息随便定义
		intent.putExtra(Intent.EXTRA_TEXT,"推荐你使用一款软件.名称为:" + selectedAppInfo.getAppname() + ",版本:"+ selectedAppInfo.getVersion());
		startActivity(intent);
	}
6.获得所有开机启动的应用名
public List<String> getStartupPackname() {
		//查询手机里面的应用程序的意图过滤器, 看哪个应用程序里面有 android.intent.action.BOOT_COMPLETED,有这个就表示会开机启动.
		List<String> packnames=new ArrayList<String>();
		PackageManager pm = getPackageManager();
		Intent intent = new Intent("android.intent.action.BOOT_COMPLETED");
		//获得
		List<ResolveInfo> infos = pm.queryBroadcastReceivers(intent, PackageManager.GET_INTENT_FILTERS);
		for(ResolveInfo info : infos){
			String receivername = info.activityInfo.name;//获得广播接收者的名字
			String packname = info.activityInfo.packageName;//包名
			packnames.add(packname);
		}
		return packnames;
}