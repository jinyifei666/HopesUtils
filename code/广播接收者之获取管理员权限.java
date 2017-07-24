//获得管理手机管理员权限
//1.首先定义一个类继承DeviceAdminReceiver
import android.app.admin.DeviceAdminReceiver;

public class MyAdmin extends DeviceAdminReceiver{

}

//2.在res中定义一个xml文件夹,里面定义一个device_admin_sample.xml文件

<?xml version="1.0" encoding="utf-8"?>
<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
  <uses-policies>
    <limit-password />
    <watch-login />
    <reset-password />
    <force-lock />
    <wipe-data />
  </uses-policies>
</device-admin>

//3.在manifest中注册Recevier监听广播
<receiver
	android:name="com.example.receiver.MyAdmin"
	android:description="@string/sample_device_admin_description"//管理员对话框的描述
	android:label="@string/sample_device_admin"//管理员对话框的标题
	android:permission="android.permission.BIND_DEVICE_ADMIN" >//只接收有这个权限的应用发的广播
	<meta-data
		android:name="android.app.device_admin"//数据类型
		android:resource="@xml/device_admin_sample" />//引用xml文件夹下的xml文件

	<intent-filter>
		<action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />//定义广播的接收的动作类型
	</intent-filter>
</receiver>
//4.在需要促发的代码中添加如下:这段代码一旦促发,就会弹出提示,确认后应用就可以获得管理员权限了
public ....{
Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	ComponentName who = new ComponentName(this, MyAdmin.class);//关联生成一个标识对象
	intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,who);
	intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"激活我可以远程锁屏,清除数据....");
	startActivity(intent);
}
//5.有了管理员权限就可以操作一些敏感动作.如:锁屏,关屏,添加密码等..
//如下添加一个解锁密码
public void onlock(){
	// 先获得策略管理者对象
	DevicePolicyManager dmp = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	// 再定义一个类继承DeviceAdminReceiver,表示这个应用要获得策略管理员权限
	// 用这个类的对象再获得一个表示这个应用的构成名对象
	ComponentName who = new ComponentName(context, MyAdmin.class);
	// 判断当前应用程序是否已经得到了管理者权限
	if (dmp.isAdminActive(who)) {
		// 后面那个0表示解锁进入时要输入密码
		dmp.resetPassword("8816", 0);
		dmp.lockNow();
	}
}