//定义缩放动画 第一个参数表示x初始大小为两倍view,第二个参数表示缩放后的大小是原大小,第三个参数是y方向初始大小,
//第四个参数是y缩放后的大小是原大小,最后两个参数是缩放时x和y方向的参照点,这里是view的中心点
ScaleAnimation sa = new ScaleAnimation(0.2f, 1.0f, 0.2f,1.0f, 0.5f, 0.5f);
sa.setDuration(600);
view.startAnimation(sa);这个方法用于启动动画
view.clearAnimation();//这个方法用于取消动画