public class StartupReceiver extends BroadcastReceiver {  
  
  @Override  
  public void onReceive(Context context, Intent intent) {  
    Intent startupintent = new Intent(context,StrongTracks.class);  
    startupintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
    context.startActivity(startupintent);  
  }  
  
}  
2)<receiver  
android:name=".StartupReceiver">  
<intent-filter>  
    <!-- 系统启动完成后会调用 -->  
    <action  
        android:name="android.intent.action.BOOT_COMPLETED">  
    </action>  
</intent-filter>  
</receiver> 