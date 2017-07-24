public AlertDialog dialog;//注意:是 AlertDialog,而不是Dialog!!!
public void vreateDialog(){
AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_normal_entry, null);
		et_normal_entry_pwd = (EditText) view
				.findViewById(R.id.et_normal_entry_pwd);
		bt_normal_entry_ok = (Button) view
				.findViewById(R.id.bt_normal_entry_ok);
		bt_normal_entry_cancle = (Button) view
				.findViewById(R.id.bt_normal_entry_cancle);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);//关健代码,表示不设宽高,那么就只显示view,这样就没有难看的标题栏了
		dialog.show();
		//当前已实现了Activity
		bt_normal_entry_ok.setOnClickListener(this);
		bt_normal_entry_cancle.setOnClickListener(this);
}