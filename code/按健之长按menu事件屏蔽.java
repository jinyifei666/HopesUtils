/**
		关健方法:event.getRepeatCount():这是重复次数。
		点后退键的时候，为了防止点得过快，触发两次后退事件，故做此设置。
	*/
方法一:
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	
		 if (event.getRepeatCount() > 0 && event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
		        return true;
		    }
		return super.dispatchKeyEvent(event);
	}
	
方法二:
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		if (event.getRepeatCount()==0&&event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
			
			if (group.isManager) {
				group.closeManager();
				return true;
			}
			if (!group.isManager) {
				group.showManager();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}