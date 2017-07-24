如何设置Activity的大小，让你的窗口看起来不再是全屏的。有些网友可能知道通过主题比如Theme.Dialog来实现，
最简单的方式是让一个Activity看起来像对话框，通过在manifest中添加android:style/Theme.Dialog的主题特性，如下面的XML片段所示：
 
<activity android:name=”MyDialogActivity”
android:theme=”@android:style/Theme.Dialog”>
</activity>
 
这将引起你的Activity的行为像一个对话框，漂浮在其下Activity的前端，部分模糊。
不过今天Android123告诉大家设置Activity不再全屏显示的原理。Android Theme也主要是通过定义Style来实现的，实现的原理大家可以直接看Android Framework中的定义，今天给一种更简单，但相对灵活的方法，比如不要Theme.Dialog中的边框，下面就一起来看下自定义Activity大小的实现方法。 

  1. 创建一个样式文件到你的工程，保存在在res/values/styles.xml，这里文件名不能随便修改，内容为，注意保存时使用UTF-8编码。 

<?xml version="1.0" encoding="utf-8"?> 
<resources> 
        <style name="Theme.Android123" parent="android:style/Theme.Dialog"> 
        <item name="android:windowBackground">@drawable/bg</item> 
</style> 
</resources>  
2. 上面我们定义的主题风格为Theme.Android123，父风格仍然从Theme.Dialog实现，但我们自定义了背景，位置在drawable/bg中，这里我们创建一个bg.xml文件放到res/drawable文件夹中，bg.xml的内容为 

<?xml version="1.0" encoding="utf-8"?> 
<shape xmlns:Android="http://schemas.android.com/apk/res/android"> 
        <padding android:left="15dp" android:top="15dp" android:right="15dp" android:bottom="15dp" /> 
        <stroke android:width="3dip" color="#000000" /> 
        <corners android:radius="5dp" /> 
<solid android:color="#ffffff" />       
</shape>  
         
里面我们定义了一个shape对象，实现背景drawable形状，其中padding代表距离边框，这里我们设置了左、上、右、下四个位置的间距。stroke可以制造出一些3D立体效果，corners是四个角，radisu属性可以设置半径，值越大越圆滑，根据运行效果你可以微调，最后soild是填充颜色，这里我们用了ffffff表示纯白。 

  3. 最后在androidmanifest.xml中，在你的activity节点加一个 android:theme属性，值为@style/Theme.Android123 即可。 