//震动效果的实现.
Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
vibrator.vibrate(200);//表示震动两百毫秒
//如果是下面这种前面参数表示震动频率,一百,两百,一百,后面的参数表示震动周期.也就是重复多少次
vibrator.vibrate(new long[]{100,200,100}, 2);
//最后在manifest.xml中注册权限
<uses-permission android:name="android.permission.VIBRATE"/>