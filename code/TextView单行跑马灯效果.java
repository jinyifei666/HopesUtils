TextView及其子类，当字符内容太长显示不下时可以省略号代替未显示的字符；省略号可以在显示区域的起始，
中间，结束位置，或者以跑马灯的方式显示文字(textview的状态为被选中)。 
<TextView android:id="@+id/textMsg"
  android:layout_width="fill_parent"
  android:layout_height="wrap_content"  
  android:text="欢迎到北京,中国首都,美丽的城市!"
  android:singleLine="true"//表示只一行显示
  android:textSize="100px"
  android:paddingTop="5dip" 
  android:ellipsize="marquee" >
  </TextView>
       其实现只需在xml中对textview的ellipsize属性做相应的设置即可。

        android:ellipsize="start"        省略号在开头        
        android:ellipsize="middle"       省略号在中间        
        android:ellipsize="end"          省略号在结尾        
        android:ellipsize="marquee"      跑马灯显示

       或者在程序中可通过setEillpsize显式设置。
       注: EditText不支持marquee这种模式。