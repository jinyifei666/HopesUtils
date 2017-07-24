在xml文件中可以使用android:textStyle=”bold”来使英文字体加粗但是不能将中文设置成粗体，

将中文设置成粗体的方法是：

    TextView tv = (TextView)findViewById(R.id.TextView01);
    TextPaint tp = tv.getPaint();
    tp.setFakeBoldText(true);

 

还有一种比较假的办法，通过设置背影，伪装加粗

    android:shadowColor=”#000000″"
    android:shadowDx=”0.2″ android:shadowDy=”0.0″ android:shadowRadius=”0.2″