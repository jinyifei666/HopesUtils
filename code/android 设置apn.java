ContentValues values = new ContentValues();
values.put(NAME, "CMCC cmwap");
values.put(APN, "cmwap");
values.put(PROXY, "10.0.0.172");

values.put(PORT, "80");
values.put(MMSPROXY, "");
values.put(MMSPORT, "");
values.put(USER, "");
values.put(SERVER, "");
values.put(PASSWORD, "");
values.put(MMSC, "");         
values.put(TYPE, "");
values.put(MCC, "460");
values.put(MNC, "00");
values.put(NUMERIC, "46000");
reURI = getContentResolver().insert(Uri.parse("content://telephony/carriers"), values);


//首选接入点"content://telephony/carriers/preferapn"