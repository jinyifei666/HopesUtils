//根据id删除短信.
public boolean deleteSms(String sms_id){
	Uri uri = Uri.withAppendedPath(Uri.parse("content://sms/conversations"), sms_id);
	int i=getContentResolver().delete(uri, null, null);
	if(i==0){
		return false;
	}else{
		return true;
	}
}
//查询短信
public Cursor querySms(){
//定义要插叙的数据库内容.
	private static final String[] PROJECTION = new String[]{
				"sms.thread_id AS _id",
				"sms.body AS snippet",
				"groups.msg_count AS msg_count",
				"sms.address AS address",
				"sms.date AS date"
		};
		Uri uri = Uri.parse("content://sms/conversations");
		/**
		 * cookie 传递对象 View
		 * uri 指定查询的位置	查询短信会话信息
		 * projection  select name, age  查询的结果（字段）
		 * selection 查询条件  where  SQL where id = ?
		 * selectionArgs	?
		 * orderBy 排序
		 */
	Cursor cursor=getContentResolver().query(null, uri, PROJECTION, null, null," date DESC");//DESC降序,这里表示根据时间降序.
	return cursor;
	//得到的结果:
	_id	        snippet			msg_count	address	date
	1			100 + 100		3			10010	1359167594934
	2			2000 + iphone5	2			10000	1359167626344
	......
}