// APN(Access Point Name)，即“接入点名称”，是您在通过手机上网时必须配置的一个参数，
// 它决定了您的手机通过哪种接入方式来访问网络,用来标识GPRS的业务种类，
// 目前分为两大类：CMWAP/UNIWAP/3GWAP(通过GPRS访问WAP业务)、CMNET/UNINET/3GNET（除了WAP以外的服务目前都用CMNET,比如连接因特网等）。
ContentValues values = new ContentValues();
values.put(NAME, "CMCC cmwap");//设为移动CMCC无线网络
values.put(APN, "cmwap");
values.put(PROXY, "10.0.0.172");

values.put(PORT, "80");
values.put(MMSPROXY, "");
values.put(MMSPORT, "");
values.put(USER, "");
values.put(SERVER, "");
values.put(PASSWORD, "");
values.put(MMSC, "");         
values.put(TYPE, "");
values.put(MCC, "460");
values.put(MNC, "00");
values.put(NUMERIC, "46000");
reURI = getContentResolver().insert(Uri.parse("content://telephony/carriers"), values);


//首选接入点"content://telephony/carriers/preferapn"