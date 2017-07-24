package com.itheima.sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import com.itheima.sqlite.bean.Person;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonDao {
	private MyOpenHelper helper;

	public PersonDao(Context context) {
		helper = new MyOpenHelper(context);
	}

	public long insert(Person p) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues(); // 这是一个Map, 用来装载要插入的列名和值
		values.put("name", p.getName());
		values.put("balance", p.getBalance());
		long id = db.insert("person", "id", values); // 向person表插入values中的数据, 得到新记录的id
		db.close();
		return id;
	}

	public int delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete("person", "id=?", new String[] { id + "" }); // 删除person表中指定条件的记录
		db.close();
		return rows;
	}

	public int update(Person p) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", p.getName());
		values.put("balance", p.getBalance());
		int rows = db.update("person", values, "id=?", new String[] { p.getId() + "" });
		db.close();
		return rows;
	}

	public Person query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("person", new String[] { "name", "balance" }, "id=?", new String[] { id + "" }, null, null, null);
		Person p = null;
		if (c.moveToNext()) { // 把游标向后移动一位, 判断是否包含数据
			String name = c.getString(c.getColumnIndex("name")); // 先根据列名获取索引, 再根据索引获取数据
			int balance = c.getInt(1); // 直接根据索引获取数据
			p = new Person(id, name, balance);
		}
		c.close();
		db.close();
		return p;
	}

	public List<Person> queryAll() {
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("person", null, null, null, null, null, null);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String name = c.getString(c.getColumnIndex("name"));
			int balance = c.getInt(c.getColumnIndex("balance"));
			persons.add(new Person(id, name, balance));
		}
		c.close();
		db.close();
		return persons;
	}

	public List<Person> queryPage(int pageNum, int pageSize) {
		int offset = (pageNum - 1) * pageSize;
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("person", null, null, null, null, null, null, offset + "," + pageSize);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String name = c.getString(c.getColumnIndex("name"));
			int balance = c.getInt(c.getColumnIndex("balance"));
			persons.add(new Person(id, name, balance));
		}
		c.close();
		db.close();
		return persons;
	}

	public Cursor queryAllCursor() {
		SQLiteDatabase db = helper.getReadableDatabase();
		return db.query("person", new String[] { "id _id", "name", "balance" }, null, null, null, null, null);
	}

}
