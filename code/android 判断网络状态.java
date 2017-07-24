<uses-permission  
    android:name="android.permission.ACCESS_NETWORK_STATE" />  
  
 private boolean getNetWorkStatus() {  
  
   boolean netSataus = false;  
   ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
  
   cwjManager.getActiveNetworkInfo();  
  
   if (cwjManager.getActiveNetworkInfo() != null) {  
   netSataus = cwjManager.getActiveNetworkInfo().isAvailable();  
   }  
  
   if (!netSataus) {  
   Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络")  
   .setMessage("是否对网络进行设置？");  
   b.setPositiveButton("是", new DialogInterface.OnClickListener() {  
   public void onClick(DialogInterface dialog, int whichButton) {  
   Intent mIntent = new Intent("/");  
   ComponentName comp = new ComponentName(  
   "com.android.settings",  
   "com.android.settings.WirelessSettings");  
   mIntent.setComponent(comp);  
   mIntent.setAction("android.intent.action.VIEW");  
   startActivityForResult(mIntent,0);   
   }  
   }).setNeutralButton("否", new DialogInterface.OnClickListener() {  
   public void onClick(DialogInterface dialog, int whichButton) {  
   dialog.cancel();  
   }  
   }).show();  
   }  
  
   return netSataus;  
   }  