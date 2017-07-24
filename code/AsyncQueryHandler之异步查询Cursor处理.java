1.异步查询之改装Cursor
//可以在mAdapter.changeCursor(cursor);传递数据之前进行,cursor数据的二次修改,但核心是要cursor.move(-1);还原Cursor索引,不然后面查不到数据.
private final class QueryHandler extends AsyncQueryHandler{

		private Context context;
		public QueryHandler(Context context) {
			super(context.getContentResolver());
			// TODO Auto-generated constructor stub
			this.context = context;
		}
		
		
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			// TODO Auto-generated method stub
			super.onQueryComplete(token, cookie, cursor);
			if(cursor == null){
				return ;
			}
			
			//在这里完成短信数据的迭代
			int size = 0;
			while(cursor.moveToNext()){
				//item原来的位置
				int position = cursor.getPosition();
				//取出日期
				long date = cursor.getLong(DATE_COLUMN_INDEX);
				String dateStr = DateFormat.getDateFormat(context).format(date);
				if(!mDatePositionMap.containsValue(dateStr)){
					mDatePositionMap.put(position + size, dateStr);
					size++;
				}
				mPositionMap.put(position + size, position);
			}
			//把cursor还原
			cursor.move(-1);
			mCursor = cursor;
			mAdapter.changeCursor(cursor);
		}
		//在这里关cursor
		@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Cursor cursor = mAdapter.getCursor();
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
	}
	}