	//用枚举定义不同的显示模式.
	enum DISPLAYMODE{
				list, edit
			}
	private DISPLAYMODE mode = DISPLAYMODE.list;//当前Activity默认的显示模式(列表)	
//添加菜单 ,这个方法只在Activity加载时执行一次
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			searchMenuItem = menu.add(0, SEARCH_MENU_ITEM_INDEX, 0, R.string.search);
			deleteMenuItem = menu.add(0, DELETE_MENU_ITEM_INDEX, 0, R.string.delete);
			backMenuItem = menu.add(0, BACK_MENU_ITEM_INDEX, 0, R.string.back);
			
			return super.onCreateOptionsMenu(menu);
		}

		//加载菜单项,这个方法每次打开菜单项都会执行.
		@Override
		public boolean onPrepareOptionsMenu(Menu menu) {
			//这里通过模式实现不同状态下
			if(mode == DISPLAYMODE.list){//列表模式
				searchMenuItem.setVisible(true);
				deleteMenuItem.setVisible(true);
				backMenuItem.setVisible(false);
			} else {//编辑模式
				searchMenuItem.setVisible(false);
				deleteMenuItem.setVisible(false);
				backMenuItem.setVisible(true);
			}	
			return super.onPrepareOptionsMenu(menu);
		}

		//菜单项点击方法
		@Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			// TODO Auto-generated method stub
			int itemId = item.getItemId();
			switch (itemId) {
			case SEARCH_MENU_ITEM_INDEX:
				//待完成
				break;
			case DELETE_MENU_ITEM_INDEX:
				changeMode(DISPLAYMODE.edit);
				
				break;
			case BACK_MENU_ITEM_INDEX:
				changeMode(DISPLAYMODE.list);
				
				break;

			}
			return super.onMenuItemSelected(featureId, item);
		}
