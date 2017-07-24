popView = this.getLayoutInflater().inflate(R.layout.pw_editbook, null);
mPopupWindow = new PopupWindow(popView, LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, true);
mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//这句是关健,但是,一定要让其在PopupWindow初始化的时候一起执行,也就是new PopupWindow的时候,不然没有效果
mPopupWindow.showAtLocation(findViewById(R.id.ll_bookshelf),Gravity.BOTTOM, 0, 0);
mPopupWindow.setFocusable(true);
mPopupWindow.update();
