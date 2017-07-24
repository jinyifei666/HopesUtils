1¡¢<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>    
2¡¢private String getLocalMacAddress() {  
    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
    WifiInfo info = wifi.getConnectionInfo();  
    return info.getMacAddress();  
  }  