//很多时候要对view里面的条目进行动态修改并
public View getView(int position, View convertView, ViewGroup parent) {
	View view;
	ViewHolder holder;
	if (convertView != null && convertView instanceof RelativeLayout) {
		view = convertView;
		holder = (ViewHolder) view.getTag();
	} else {
		view = View.inflate(getApplicationContext(),R.layout.list_app_item, null);
		holder = new ViewHolder();
		//.......
		holder.ll_container = (LinearLayout) view.findViewById(R.id.ll_appstatus_container);//首先要获得要修改layout对象
		view.setTag(holder);
	}
	//每次添加之前要清空一下layout因为这里用holder复用了,不然layout里面的view还在.
	holder.ll_container.removeAllViews();
	//下面再根据条件给layout添加子view
	if(appinfo.isUsecontact()){
		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.contact);
		holder.ll_container.addView(iv, DensityUtil.dip2px(getApplicationContext(), 25), DensityUtil.dip2px(getApplicationContext(), 25));
	}if(appinfo.isUsegps()){
		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.gps);
		holder.ll_container.addView(iv, DensityUtil.dip2px(getApplicationContext(), 25), DensityUtil.dip2px(getApplicationContext(), 25));
	}if(appinfo.isUsesms()){
		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.sms);
		holder.ll_container.addView(iv, DensityUtil.dip2px(getApplicationContext(), 25), DensityUtil.dip2px(getApplicationContext(), 25));
	}if(appinfo.isUsenet()){
		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.net);
		holder.ll_container.addView(iv, DensityUtil.dip2px(getApplicationContext(), 25), DensityUtil.dip2px(getApplicationContext(), 25));
	}
	//ll_container中总view中的一个layout,所以ll_container修改了,总view自然会修改,最后返回view就可以了.
	return view;
}



	static class ViewHolder {
		TextView tv_name;
		TextView tv_location;
		TextView tv_version;
		ImageView iv_icon;
		LinearLayout ll_container;
	}
	//定义一下ll_container的xml
	<LinearLayout
        android:id="@+id/ll_appstatus_container"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentTop="true"
        android:orientation="horizontal" >
    </LinearLayout>