< activity android:name="MyActivity"  
android:configChanges="orientation|keyboardHidden"> 


public void onConfigurationChanged(Configuration newConfig) {  
  
   super.onConfigurationChanged(newConfig);  
  
if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {  
           //加入横屏要处理的代码  
  
}else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {  
  
           //加入竖屏要处理的代码  
  
}  
  
  
}  