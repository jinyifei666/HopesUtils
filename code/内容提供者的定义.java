package com.itheima.sqlite.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.itheima.sqlite.dao.MyOpenHelper;

public class SQLiteProvider extends ContentProvider {
	private MyOpenHelper helper;
	private UriMatcher matcher;
	private static final int PERSON_ID = 0;
	private static final int PERSON = 1;
	private static final int USER = 2;
	
	@Override
	public boolean onCreate() {		// 创建的时候自动执行. 第一次安装到手机上时创建. 手机开机后第一次被访问时创建.
		System.out.println("onCreate");
		helper = new MyOpenHelper(getContext());
		matcher = new UriMatcher(UriMatcher.NO_MATCH);					// 创建一个Uri匹配器, 用来识别传入的Uri, 分辨表名
		matcher.addURI("com.itheima.SQLiteProvider", "person", PERSON);	// 给匹配器添加一个Uri, 以备在增删改查中识别
		matcher.addURI("com.itheima.SQLiteProvider", "user", USER);
		matcher.addURI("com.itheima.SQLiteProvider", "person/#", PERSON_ID);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = helper.getReadableDatabase();
		switch (matcher.match(uri)) {
			case PERSON_ID:
				selection = "id=" + ContentUris.parseId(uri);	// 截取Uri中的id, 作为查询条件
			case PERSON:
				return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
			case USER:
				return db.query("user", projection, selection, selectionArgs, null, null, sortOrder);
			default:
				throw new IllegalArgumentException("不能识别的Uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = helper.getReadableDatabase();
		switch (matcher.match(uri)) {
			case PERSON:
				long id = db.insert("person", "id", values);
				db.close();
				return ContentUris.withAppendedId(uri, id);		// 把id拼在Uri后面
			default:
				throw new IllegalArgumentException("不能识别的Uri: " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = helper.getReadableDatabase();
		switch (matcher.match(uri)) {
			case PERSON_ID:
				selection = "id=" + ContentUris.parseId(uri);
			case PERSON:
				int count = db.delete("person", selection, selectionArgs);
				db.close();
				return count;
			default:
				throw new IllegalArgumentException("不能识别的Uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = helper.getReadableDatabase();
		switch (matcher.match(uri)) {
			case PERSON_ID:
				selection = "id=" + ContentUris.parseId(uri);
			case PERSON:
				int count = db.update("person", values, selection, selectionArgs);
				db.close();
				return count;
			default:
				throw new IllegalArgumentException("不能识别的Uri: " + uri);
		}
	}
	
	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
			case PERSON_ID:
				return "vnd.android.cursor.item/person";	// mimetype, 单条person
			case PERSON:
				return "vnd.android.cursor.dir/person";		// mimetype, 多条person
			default:
				throw new IllegalArgumentException("不能识别的Uri: " + uri);
		}
	}

}
