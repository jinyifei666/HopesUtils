Android中为你的应用程序添加桌面快捷方式
你只需要在你的应用程序启动的第一个Activity里添加这样的一个方法：
1.注意权限!!!:<uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>//需要的权限
public class AndroidLayoutActivity extendsActivity {
       /**Called when the activity is first created. */
       @Override
       publicvoid onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.view_personal_info);
              SharedPreferencespreferences = getSharedPreferences("first",
                            Context.MODE_PRIVATE);
              boolean isFirst = preferences.getBoolean("isfrist", true);
              if(isFirst) {
                     createDeskShortCut();
              }
              SharedPreferences.Editoreditor = preferences.edit();
              editor.putBoolean("isfrist",false);
              editor.commit();
       }
 
       /**
        * 创建快捷方式
        */
       publicvoid createDeskShortCut() {
 
              Log.i("coder","------createShortCut--------");
              //创建快捷方式的Intent
              Intent shortcutIntent = new Intent( "com.android.launcher.action.INSTALL_SHORTCUT");
              //不允许重复创建
              shortcutIntent.putExtra("duplicate",false);
              //需要显示的名称
              shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)); 
              //快捷图片
              Parcelableicon = Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.drawable.ic_launcher);
              shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,icon);
              Intentintent = new Intent(getApplicationContext(),AndroidLayoutActivity.class);
              //下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
              intent.setAction("android.intent.action.MAIN");
              intent.addCategory("android.intent.category.LAUNCHER");
              //点击快捷图片，运行的程序主入口,也就是按了图标后,进入这个intent定义的AndroidLayoutActivity
              shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent);
              //发送广播。OK
              sendBroadcast(shortcutIntent);
       }
}