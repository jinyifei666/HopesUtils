    	ContentValues values = new ContentValues();
    	values.put("address", "123456789");
    	values.put("body", "haha");
    	values.put("date", "135123000000");
    	getContentResolver().insert(Uri.parse("content://sms/sent"), values);