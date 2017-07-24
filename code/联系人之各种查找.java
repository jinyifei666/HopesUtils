
	/**
	 * 查找手机里所有的联系人信息
	 * @return 返回联系人集合map key表示名字,values表示这个名字表示的所有号码
	*/
	public HashMap<String,ArrayList<String>>  startQueryAll() {
		ContentResolver cr = getContentResolver();
		HashMap<String,ArrayList<String>> hs=new HashMap<String,ArrayList<String>>();
		Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
		CommonDataKinds.Phone.NUMBER, CommonDataKinds.Phone.DISPLAY_NAME }, null, null, null);
		while (phone.moveToNext()) {
		String strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		String name = phone.getString(phone.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME));	//这里处理一个联系人有几个号码的情况:一个联系人有多个号码在数据库中是多条记录,这里先通过名字获取集合,如果之前已经存在联系人,那么就往value里的list添加号码就可以了.这样就处理了
		ArrayList<String> ad=hs.get(name);
		if(ad==null){
			ad=new ArrayList<String>();
			ad.add(strPhoneNumber);
			hs.put(name, ad);
		} else {
			ad.add(strPhoneNumber);
			}
		}
		phone.close();
		return hs;
	}
	//通过电话号码查询联系人
	public string  startQuery(String address) {
			//联系人  所需字段
			private  final String[] CONTACT_PROJECTION = new String[]{
				PhoneLookup._ID,
				PhoneLookup.DISPLAY_NAME,
				PhoneLookup.NUMBER
			};
			//根据电话号码 查询联系人信息  （名称）
			String name = null;
			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
			Cursor contact_Cursor = context.getContentResolver().query(uri, CONTACT_PROJECTION, null, null, null);
			if(contact_Cursor.moveToFirst()){
				//查询到联系人的信息
				name = contact_Cursor.getString(DISPLAY_NAME_COLUMN_INDEX);
			}
			contact_Cursor.close();
			return mame;
	}
	//查询通过号码模糊查询联系人,得到cursor
	public Cursor startQueryVague(String constraint) {
		//注意做非空判断
			if(TextUtils.isEmpty(constraint)){
				return null;
			}
			//通过输入的数据模糊查询数据库
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			//select * from sms where number like '%1%'
			String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " like '%" + constraint + "%'";
			Cursor c = context.getContentResolver().query(uri,
														new String[]{ContactsContract.Contacts._ID,
														ContactsContract.Contacts.DISPLAY_NAME,
														ContactsContract.CommonDataKinds.Phone.NUMBER},
														selection, null, null);
			return c;
		}
		
		/**
     * 查询短信的数据
     * getContentResolver().query()与
     * managedQuery(uri, projection, selection, selectionArgs, sortOrder) 二者区别是:managedQuery不用手动的去管理cursor,让activity去帮我们管理
     * 如果直接查询，就是在主线程进行的。
     * 可以采用android提供的异步框架,
     * 使用范围：只能去访问我们的ContentProvider所提供的数据
     */
	 //查找特定id的联系人.
    private void startQuery(String thread_ids) {
    	  String[] CONVERSATION_PROJECTION = new String[]{"sms.thread_id as _id",
    		"snippet",
    		"msg_count",
    		"sms.address as address",
    		"sms.date as date"};
    	Uri uri = Uri.parse("content://sms/conversations");
    	// select * from table where thread_id in (1,2,4)
    	if(thread_ids != null){
    		String where = Sms.THREAD_ID + " in " + thread_ids;
        	mQueryHandler.startQuery(0, null, uri, CONVERSATION_PROJECTION, where, null, " date desc");
    	}else{
        	mQueryHandler.startQuery(0, null, uri, CONVERSATION_PROJECTION, null, null, " date desc");
    	}
	}