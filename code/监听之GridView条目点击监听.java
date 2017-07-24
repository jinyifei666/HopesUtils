//监听之GridView条目点击监听
gv_home = (GridView) findViewById(R.id.gv_home);
gv_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				//通过索引,从0开始,从左到右,从上到下
				switch (position) {
				case 0:
					// 判断用户是否设置过密码.
					if (isSetupPwd()) {
						showNormalEntryDialog();
					} else {
						showFirstEntryDialog();
					}
					break;
				
				//...............
				case 8:
					intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					startActivity(intent);
					break;
				}
			}
		});