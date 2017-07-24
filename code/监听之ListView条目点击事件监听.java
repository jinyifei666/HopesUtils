ListView条目点击事件监听 这个与GridView是一样的.
lv_select_contact.setOnItemClickListener(new OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		ContactInfo info = infos.get(position);
		String phone = info.getPhone();
		Intent data = new Intent();
		data.putExtra("phone", phone);
		setResult(0, data);
		finish();
	}
});