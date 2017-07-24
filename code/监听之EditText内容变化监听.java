//添加一个EditText内容变化的监听器
		et_query_number.addTextChangedListener(new TextWatcher() {
			//内容变化就会执行
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			//这里就可以作相应的处理,比如:实现边输入边查询
				String number = s.toString();
				String address = AddressDao.getAddress(number);
				tv_query_address.setText("归属地为:"+address);
			}
			//内容变化前执行的方法
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			//内容变化后执行的方法
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});