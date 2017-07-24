ViewPager的onPageChangeListener里面的一些方法参数：
ViewPager的onPageChangeListener里面的一些方法参数：
1.arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
onPageSelected(int arg0){

}

2.arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
public void onPageScrollStateChanged(int arg0) {
    // TODO Auto-generated method stub
    
   
   } 

 
3.表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法。
public void onPageScrolled(int arg0, float arg1, int arg2) {
    // TODO Auto-generated method stub
   }