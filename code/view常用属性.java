view.setFocusable(false);//不可获得焦点 反之true
view.setEnabled(false);//设置为不可用 反之true
//{@IntToString(from=0, to="VISIBLE"), @IntToString(from=4, to="INVISIBLE"), @IntToString(from=8, to="GONE")})
int i = rLzoomView.getVisibility();//看view是否可见
listView.setDivider(null);// 定义不显示默认的线条
listView.setVerticalScrollBarEnabled(false);//设置不要显示拖动条