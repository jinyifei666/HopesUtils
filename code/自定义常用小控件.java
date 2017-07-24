//分隔线的画法
<View
	android:layout_alignParentBottom="true"
	android:background="@drawable/listview_devider"//这个背景色可以设其它的,就成了不同的颜色风格的线条
	android:layout_width="fill_parent"
	android:layout_height="1dip" >//线条宽度由这个控制
</View>

//自定义两个小点,一个为开一个为关,实现两个小点的属性
    <style name="image_on_style" parent="@style/image_star_style">
        <item name="android:src">@android:drawable/presence_online</item>
         <item name="android:paddingLeft">2dip</item>
    </style>

    <style name="image_off_style" parent="@style/image_star_style">
        <item name="android:src">@android:drawable/presence_invisible</item>
         <item name="android:paddingLeft">2dip</item>
    </style>
